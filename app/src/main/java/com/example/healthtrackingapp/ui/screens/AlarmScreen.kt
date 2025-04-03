package com.example.healthtrackingapp.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.AlarmAdd
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.*
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.healthtrackingapp.R
import com.example.healthtrackingapp.requestExactAlarmPermission
import com.example.healthtrackingapp.viewmodels.AlarmReceiver
import org.json.JSONArray
import org.json.JSONObject
import java.util.Calendar

data class AlarmItem(
    val id: Int,
    val hour: Int,
    val minute: Int,
    val enabled: Boolean,
    val repeatDays: Set<DayOfWeek>,
    val label: String
)
// 1. Thêm các hàm tiện ích để chuyển đổi giữa Set<DayOfWeek> và JSONArray
fun dayOfWeekSetToJsonArray(repeatDays: Set<DayOfWeek>): JSONArray {
    val jsonArray = JSONArray()
    repeatDays.forEach { day ->
        jsonArray.put(day.name)
    }
    return jsonArray
}
fun jsonArrayToDayOfWeekSet(jsonArray: JSONArray?): Set<DayOfWeek> {
    if (jsonArray == null) return emptySet()

    val repeatDaysSet = mutableSetOf<DayOfWeek>()
    for (i in 0 until jsonArray.length()) {
        try {
            val dayString = jsonArray.getString(i)
            repeatDaysSet.add(DayOfWeek.valueOf(dayString))
        } catch (e: Exception) {
            Log.e("AlarmApp", "Lỗi khi chuyển đổi ngày: ${e.message}")
        }
    }
    return repeatDaysSet
}

// 2. Sửa lại hàm loadAlarms để xử lý dữ liệu đúng
fun loadAlarms(context: Context, onAlarmsLoaded: (List<AlarmItem>) -> Unit) {
    val sharedPref = context.getSharedPreferences("AlarmPrefs", Context.MODE_PRIVATE)
    val alarmList = mutableListOf<AlarmItem>()

    sharedPref.all.forEach { (key, value) ->
        if (key.startsWith("alarm_") && value is String) {
            try {
                val alarmJson = JSONObject(value)

                // Đọc danh sách ngày lặp lại
                val repeatDaysArray = alarmJson.optJSONArray("repeatDays")
                val repeatDaysSet = jsonArrayToDayOfWeekSet(repeatDaysArray)

                val alarm = AlarmItem(
                    id = key.removePrefix("alarm_").toInt(),
                    hour = alarmJson.getInt("hour"),
                    minute = alarmJson.getInt("minute"),
                    enabled = alarmJson.optBoolean("enabled", false),
                    repeatDays = repeatDaysSet,
                    label = alarmJson.optString("label", "Báo thức")
                )
                alarmList.add(alarm)
            } catch (e: Exception) {
                Log.e("AlarmApp", "Lỗi khi đọc báo thức từ SharedPreferences: ${e.message}")
            }
        }
    }

    onAlarmsLoaded(alarmList)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmScreen(navController: NavHostController) {
    val context = LocalContext.current

    var alarms by remember {
        mutableStateOf(
            listOf<AlarmItem>()
        )
    }
    LaunchedEffect(Unit) {
        loadAlarms(context) { loadedAlarms ->
            alarms = loadedAlarms // Cập nhật biến trạng thái
        }
    }

    var showAddAlarmDialog by remember { mutableStateOf(false) }
    var selectedAlarm by remember { mutableStateOf<AlarmItem?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Báo thức") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            Icons.Default.ArrowBackIosNew,
                            contentDescription = "Back",
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    requestExactAlarmPermission(context)
                    selectedAlarm = null
                    showAddAlarmDialog = true
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Outlined.AlarmAdd,
                    contentDescription = "Thêm báo thức",
                    tint = Color.White
                )
            }
        }
    ) { innerPadding ->
        Image(
            painter = painterResource(id = R.drawable.nen_app),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            if (alarms.isEmpty()) {
                EmptyAlarmView(onAddClick = {
                    selectedAlarm = null
                    showAddAlarmDialog = true
                })
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = alarms,
                        key = { it.id }
                    ) { alarm ->
                        AlarmItemCard(
                            alarm = alarm,
                            onToggleEnabled = { enabled ->
                                // Cập nhật trạng thái trong danh sách
                                val updatedAlarms = alarms.map {
                                    if (it.id == alarm.id) it.copy(enabled = enabled) else it
                                }
                                alarms = updatedAlarms

                                // Cập nhật trạng thái trong SharedPreferences
                                val updatedAlarm = updatedAlarms.find { it.id == alarm.id }
                                if (updatedAlarm != null) {
                                    saveAlarm(context, updatedAlarm)

                                    // Cập nhật hoặc hủy báo thức
                                    if (enabled) {
                                        setAlarm(context, updatedAlarm)
                                    } else {
                                        cancelAlarm(context, updatedAlarm)
                                    }
                                }
                            },
                            onEditClick = {
                                selectedAlarm = alarm
                                showAddAlarmDialog = true
                            },
                            onDeleteClick = {
                                // Xóa báo thức khỏi danh sách
                                alarms = alarms.filter { it.id != alarm.id }

                                // Hủy báo thức
                                cancelAlarm(context, alarm)

                                // Xóa khỏi SharedPreferences
                                deleteAlarm(context, alarm.id)
                            }
                        )
                    }
                }
            }
        }
    }

    // Trong AlarmScreen, phần xử lý khi lưu báo thức cần được cập nhật
    if (showAddAlarmDialog) {
        AlarmDialog(
            alarm = selectedAlarm,
            onDismiss = { showAddAlarmDialog = false },
            onSave = { alarm ->
                if (selectedAlarm != null) {
                    // Hủy báo thức cũ trước khi cập nhật
                    cancelAlarm(context, selectedAlarm!!)

                    // Cập nhật báo thức với ID giữ nguyên
                    val updatedAlarm = alarm.copy(id = selectedAlarm!!.id)
                    alarms = alarms.map { if (it.id == updatedAlarm.id) updatedAlarm else it }

                    // Lưu vào SharedPreferences sau khi cập nhật danh sách
                    saveAlarm(context, updatedAlarm)

                    // Đặt báo thức
                    setAlarm(context, updatedAlarm)
                } else {
                    // Đây là báo thức mới, tạo ID mới
                    val newId = if (alarms.isEmpty()) 1 else alarms.maxOf { it.id } + 1
                    val newAlarm = alarm.copy(id = newId)

                    // Cập nhật danh sách báo thức
                    alarms = alarms + newAlarm

                    // Lưu vào SharedPreferences sau khi đã tạo ID mới
                    saveAlarm(context, newAlarm)

                    // Đặt báo thức
                    setAlarm(context, newAlarm)
                }
                showAddAlarmDialog = false
            }
        )
    }
}

@Composable
fun EmptyAlarmView(onAddClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val infiniteTransition = rememberInfiniteTransition(label = "pulse")
        val scale by infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 1.2f,
            animationSpec = infiniteRepeatable(
                animation = tween(1000),
                repeatMode = RepeatMode.Reverse
            ), label = "scale"
        )

        Icon(
            imageVector = Icons.Default.Alarm,
            contentDescription = "Không có báo thức",
            modifier = Modifier
                .size(80.dp)
                .scale(scale),
            tint = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Chưa có báo thức nào",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Nhấn nút bên dưới để thêm báo thức mới",
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onAddClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Thêm báo thức")
        }
    }
}

@Composable
fun AlarmItemCard(
    alarm: AlarmItem,
    onToggleEnabled: (Boolean) -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onEditClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (alarm.enabled) Color.White else Color(0xFFF5F5F5)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = String.format("%02d:%02d", alarm.hour, alarm.minute),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (alarm.enabled) Color.Black else Color.Gray
                )

                Spacer(modifier = Modifier.height(4.dp))

                if (alarm.label.isNotEmpty()) {
                    Text(
                        text = alarm.label,
                        fontSize = 14.sp,
                        color = if (alarm.enabled) Color.DarkGray else Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    DayOfWeek.values().forEach { day ->
                        val isSelected = alarm.repeatDays.contains(day)
                        val dayChar = day.getDisplayName(TextStyle.NARROW, Locale.getDefault()).first()

                        Box(
                            modifier = Modifier
                                .size(22.dp)
                                .clip(CircleShape)
                                .background(
                                    if (isSelected && alarm.enabled) MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                                    else Color.Transparent
                                )
                                .border(
                                    width = 1.dp,
                                    color = if (isSelected && alarm.enabled) Color.Transparent
                                    else Color.Gray.copy(alpha = 0.5f),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = dayChar.toString(),
                                fontSize = 12.sp,
                                color = if (isSelected && alarm.enabled) Color.White else Color.Gray
                            )
                        }
                    }
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onDeleteClick
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Xóa báo thức",
                        tint = Color.Gray
                    )
                }

                Switch(
                    checked = alarm.enabled,
                    onCheckedChange = onToggleEnabled,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.primary,
                        checkedTrackColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }
        }
    }
}

@Composable
fun AlarmDialog(
    alarm: AlarmItem?,
    onDismiss: () -> Unit,
    onSave: (AlarmItem) -> Unit
) {
    val context = LocalContext.current
    val initialHour = alarm?.hour ?: 7
    val initialMinute = alarm?.minute ?: 0

    var hour by remember { mutableStateOf(initialHour) }
    var minute by remember { mutableStateOf(initialMinute) }
    var label by remember { mutableStateOf(alarm?.label ?: "") }
    var repeatDays by remember { mutableStateOf(alarm?.repeatDays ?: setOf<DayOfWeek>()) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (alarm != null) "Sửa báo thức" else "Thêm báo thức mới",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Time Picker
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Hours
                    TimePickerWheel(
                        value = hour,
                        range = 0..23,
                        onValueChange = { hour = it }
                    )

                    Text(
                        text = ":",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    // Minutes
                    TimePickerWheel(
                        value = minute,
                        range = 0..59,
                        onValueChange = { minute = it }
                    )
                }

                // Days of week
                Text(
                    text = "Lặp lại",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(vertical = 8.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val dayNames = mapOf(
                        DayOfWeek.MONDAY to "2",
                        DayOfWeek.TUESDAY to "3",
                        DayOfWeek.WEDNESDAY to "4",
                        DayOfWeek.THURSDAY to "5",
                        DayOfWeek.FRIDAY to "6",
                        DayOfWeek.SATURDAY to "7",
                        DayOfWeek.SUNDAY to "C"
                    )
                    DayOfWeek.values().forEach { day ->
                        val isSelected = repeatDays.contains(day)
                        val dayName = dayNames[day] ?: "" // Lấy ký tự theo danh sách cố định

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.clickable {
                                repeatDays = if (isSelected) {
                                    repeatDays - day
                                } else {
                                    repeatDays + day
                                }
                            }
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (isSelected) MaterialTheme.colorScheme.primary
                                        else Color.LightGray.copy(alpha = 0.3f)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = dayName, // Sử dụng tên ngày từ danh sách cố định
                                    color = if (isSelected) Color.White else Color.Black,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                // Label input
                OutlinedTextField(
                    value = label,
                    onValueChange = { label = it },
                    label = { Text("Nhãn báo thức") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    singleLine = true,
                    trailingIcon = {
                        if (label.isNotEmpty()) {
                            IconButton(onClick = { label = "" }) {
                                Icon(Icons.Default.Clear, "Xóa")
                            }
                        }
                    }
                )

                // Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Hủy")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // LƯU Ý: Đối với báo thức mới, KHÔNG lưu trực tiếp ở đây với ID = 0
                    Button(
                        onClick = {
                            val newAlarm = AlarmItem(
                                id = alarm?.id ?: 0, // Vẫn giữ ID = 0 cho báo thức mới tạm thời
                                hour = hour,
                                minute = minute,
                                enabled = alarm?.enabled ?: true,
                                repeatDays = repeatDays,
                                label = label.trim()
                            )
                            // Không lưu vào SharedPreferences ở đây, mà chỉ trả về đối tượng báo thức
                            onSave(newAlarm)
                        }
                    ) {
                        Text("Lưu")
                    }
                }
            }
        }
    }
}

// 4. Tạo hàm saveAlarm riêng biệt
fun saveAlarm(context: Context, alarm: AlarmItem) {
    val sharedPref = context.getSharedPreferences("AlarmPrefs", Context.MODE_PRIVATE)

    val alarmData = JSONObject().apply {
        put("id", alarm.id)
        put("hour", alarm.hour)
        put("minute", alarm.minute)
        put("enabled", alarm.enabled)
        put("repeatDays", dayOfWeekSetToJsonArray(alarm.repeatDays))
        put("label", alarm.label)
    }.toString()

    with(sharedPref.edit()) {
        putString("alarm_${alarm.id}", alarmData)
        apply()
    }
}

// 5. Thêm hàm để xóa báo thức khỏi SharedPreferences
fun deleteAlarm(context: Context, alarmId: Int) {
    val sharedPref = context.getSharedPreferences("AlarmPrefs", Context.MODE_PRIVATE)

    with(sharedPref.edit()) {
        remove("alarm_$alarmId")
        apply()
    }

    // Cũng cần hủy báo thức đã cài đặt
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, AlarmReceiver::class.java)

    val pendingIntentFlag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    } else {
        PendingIntent.FLAG_UPDATE_CURRENT
    }

    val pendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, pendingIntentFlag)
    alarmManager.cancel(pendingIntent)

    // Hủy báo thức cho từng ngày trong tuần
    for (day in DayOfWeek.values()) {
        val dayPendingIntent = PendingIntent.getBroadcast(
            context,
            alarmId * 10 + day.ordinal,
            intent,
            pendingIntentFlag
        )
        alarmManager.cancel(dayPendingIntent)
    }
}

@Composable
fun TimePickerWheel(
    value: Int,
    range: IntRange,
    onValueChange: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .width(80.dp)
            .height(120.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                onClick = {
                    val newValue = if (value == range.first) range.last else value - 1
                    onValueChange(newValue)
                }
            ) {
                Icon(Icons.Default.KeyboardArrowUp, null)
            }

            Text(
                text = String.format("%02d", value),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            IconButton(
                onClick = {
                    val newValue = if (value == range.last) range.first else value + 1
                    onValueChange(newValue)
                }
            ) {
                Icon(Icons.Default.KeyboardArrowDown, null)
            }
        }
    }
}


fun setAlarm(context: Context, alarm: AlarmItem) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, AlarmReceiver::class.java).apply {
        putExtra("ALARM_ID", alarm.id)
        putExtra("ALARM_LABEL", alarm.label)
    }

    // Sử dụng alarm.id làm requestCode để tránh ghi đè
    val pendingIntentFlag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    } else {
        PendingIntent.FLAG_UPDATE_CURRENT
    }

    val pendingIntent = PendingIntent.getBroadcast(context, alarm.id, intent, pendingIntentFlag)

    val calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, alarm.hour)
        set(Calendar.MINUTE, alarm.minute)
        set(Calendar.SECOND, 0)

        // Nếu thời gian đã qua, đặt cho ngày hôm sau
        if (timeInMillis < System.currentTimeMillis()) {
            add(Calendar.DAY_OF_YEAR, 1)
        }
    }

    if (alarm.enabled) {
        if (alarm.repeatDays.isNotEmpty()) {
            // Xử lý báo thức lặp lại
            setupRepeatingAlarm(context, alarmManager, pendingIntent, alarm)
        } else {
            // Báo thức một lần
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            } else {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            }
        }
    } else {
        // Hủy báo thức nếu bị vô hiệu hóa
        alarmManager.cancel(pendingIntent)
    }
}

private fun setupRepeatingAlarm(
    context: Context,
    alarmManager: AlarmManager,
    pendingIntent: PendingIntent,
    alarm: AlarmItem
) {
    // Triển khai báo thức lặp lại theo ngày trong tuần
    for (day in DayOfWeek.values()) {
        if (alarm.repeatDays.contains(day)) {
            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, alarm.hour)
                set(Calendar.MINUTE, alarm.minute)
                set(Calendar.SECOND, 0)

                // Điều chỉnh đến ngày trong tuần tiếp theo
                val currentDayOfWeek = get(Calendar.DAY_OF_WEEK)
                val targetDayOfWeek = when (day) {
                    DayOfWeek.MONDAY -> Calendar.MONDAY
                    DayOfWeek.TUESDAY -> Calendar.TUESDAY
                    DayOfWeek.WEDNESDAY -> Calendar.WEDNESDAY
                    DayOfWeek.THURSDAY -> Calendar.THURSDAY
                    DayOfWeek.FRIDAY -> Calendar.FRIDAY
                    DayOfWeek.SATURDAY -> Calendar.SATURDAY
                    DayOfWeek.SUNDAY -> Calendar.SUNDAY
                }

                if (get(Calendar.DAY_OF_WEEK) != targetDayOfWeek) {
                    add(Calendar.DAY_OF_WEEK, (targetDayOfWeek - currentDayOfWeek + 7) % 7)
                }

                // Đảm bảo thời gian trong tương lai
                if (timeInMillis < System.currentTimeMillis()) {
                    add(Calendar.DAY_OF_YEAR, 7)
                }
            }

            // Tạo PendingIntent riêng cho mỗi ngày
            val dayIntent = Intent(context, AlarmReceiver::class.java).apply {
                putExtra("ALARM_ID", alarm.id)
                putExtra("ALARM_DAY", day.toString())
                putExtra("ALARM_LABEL", alarm.label)
            }

            val dayPendingIntent = PendingIntent.getBroadcast(
                context,
                alarm.id * 10 + day.ordinal, // Tạo requestCode duy nhất
                dayIntent,
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                } else {
                    PendingIntent.FLAG_UPDATE_CURRENT
                }
            )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    dayPendingIntent
                )
            } else {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    dayPendingIntent
                )
            }
        }
    }
}

fun cancelAlarm(context: Context, alarm: AlarmItem) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, AlarmReceiver::class.java)

    val pendingIntentFlag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    } else {
        PendingIntent.FLAG_UPDATE_CURRENT
    }

    val pendingIntent = PendingIntent.getBroadcast(context, alarm.id, intent, pendingIntentFlag)
    alarmManager.cancel(pendingIntent)

    // Hủy cả báo thức lặp lại nếu có
    if (alarm.repeatDays.isNotEmpty()) {
        for (day in DayOfWeek.values()) {
            if (alarm.repeatDays.contains(day)) {
                val dayIntent = Intent(context, AlarmReceiver::class.java)
                val dayPendingIntent = PendingIntent.getBroadcast(
                    context,
                    alarm.id * 10 + day.ordinal,
                    dayIntent,
                    pendingIntentFlag
                )
                alarmManager.cancel(dayPendingIntent)
            }
        }
    }
}