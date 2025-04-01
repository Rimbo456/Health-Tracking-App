package com.example.healthtrackingapp.ui.screens

import android.app.TimePickerDialog
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardDoubleArrowLeft
import androidx.compose.material.icons.filled.KeyboardDoubleArrowRight
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.DirectionsBike
import androidx.compose.material.icons.rounded.DirectionsRun
import androidx.compose.material.icons.rounded.FitnessCenter
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.NoteAlt
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Pool
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Save
import androidx.compose.material.icons.rounded.SelfImprovement
import androidx.compose.material.icons.rounded.SportsMartialArts
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Popup
import com.example.healthtrackingapp.R
import com.example.healthtrackingapp.ui.components.Calendar
import com.example.healthtrackingapp.ui.components.ItemNof
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit
import kotlin.time.Duration
import androidx.core.graphics.toColorInt
import androidx.navigation.NavHostController

@Composable
fun NotificationScreen(
    navController: NavHostController
) {
    var currentTime by remember { mutableStateOf(getTime()) }
    var showNotifications by remember { mutableStateOf(false) }
    var showWorkout by remember { mutableStateOf(false) }
    var selectedWorkout by remember { mutableStateOf("Chọn bài tập") }
    var workoutOptions = listOf("🏃 Chạy bộ", "💪 Chống đẩy", "🦵 Squat", "🤸 Gập bụng")
    var showDropdown by remember { mutableStateOf(false) }
    var workoutTime by remember { mutableStateOf(60) }
    var isTimerRunning by remember { mutableStateOf(false) }
    var intensityValue by remember { mutableStateOf(3f) }
    var selectedFatigue by remember { mutableStateOf(-1) }
    var noteText by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var fatigueOptions = listOf("Thoải mái", "Hơi mệt", "Mệt", "Kiệt sức")

    val db = FirebaseFirestore.getInstance()
    var user by remember { mutableStateOf(Firebase.auth.currentUser) }

    LaunchedEffect(Unit) {
        while (true) {
            currentTime = getTime()
            delay(1000L) // Cập nhật mỗi giây
        }
    }
    Image(
        painter = painterResource(id = R.drawable.nen_app),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { currentTime = getTime() }) {
                    Icon(
                        Icons.Filled.Refresh,
                        contentDescription = "Refresh Time",
                        tint = Color.Black
                    )
                }
                Text(
                    text = currentTime,
                    fontSize = 22.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Row {
                    IconButton(
                        onClick = {
                            navController.navigate("alarmscreen")
                        }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_remind_no_touch_96),
                            contentDescription = "Alarm",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    IconButton(onClick = { showNotifications = !showNotifications }) {
                        Image(
                            painter = if (showNotifications) {
                                painterResource(id = R.drawable.ic_bell_touch_420)
                            } else {
                                painterResource(id = R.drawable.ic_bell_no_touch_420)
                            },
                            contentDescription = "Notifications",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }

            //Lịch
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(10.dp, RoundedCornerShape(16.dp))
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Calendar()
            }
            // Khu vực luyện tập thể dục
            Box(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Card chính với hiệu ứng gradient và đường cong mềm mại
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(1f),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            Color(0xFF7F7FD5),
                                            Color(0xFF86A8E7),
                                            Color(0xFF91EAE4)
                                        ),
                                        start = Offset(0f, 0f),
                                        end = Offset(
                                            Float.POSITIVE_INFINITY,
                                            Float.POSITIVE_INFINITY
                                        )
                                    )
                                )
                                .padding(20.dp)
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.FitnessCenter,
                                        contentDescription = "Thể dục",
                                        tint = Color.White,
                                        modifier = Modifier.size(32.dp)
                                    )
                                    Text(
                                        "Luyện tập thể dục",
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        style = TextStyle(
                                            shadow = Shadow(
                                                color = Color(0x33000000),
                                                offset = Offset(2f, 2f),
                                                blurRadius = 3f
                                            )
                                        )
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Button(
                                    onClick = { showWorkout = !showWorkout },
                                    shape = RoundedCornerShape(20.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.White.copy(alpha = 0.9f),
                                        contentColor = Color(0xFF7F7FD5)
                                    ),
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp)
                                        .height(48.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Rounded.PlayArrow,
                                            contentDescription = "Bắt đầu",
                                            modifier = Modifier.size(24.dp)
                                        )
                                        Text(
                                            "Bắt đầu tập luyện",
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Phần chọn môn thể thao và hẹn giờ
                    AnimatedVisibility(
                        visible = showWorkout,
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically()
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                modifier = Modifier.padding(20.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.SportsMartialArts,
                                        contentDescription = "Thể thao",
                                        tint = Color(0xFF7F7FD5),
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Text(
                                        "Chọn môn thể thao",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF333333)
                                    )
                                }

                                OutlinedCard(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(16.dp),
                                    border = BorderStroke(1.dp, Color(0xFFE0E0E0))
                                ) {
                                    Box(modifier = Modifier.fillMaxWidth()) {
                                        Button(
                                            onClick = { showDropdown = !showDropdown },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(56.dp),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color.White,
                                                contentColor = Color(0xFF333333)
                                            ),
                                            shape = RoundedCornerShape(16.dp)
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                Text(
                                                    selectedWorkout,
                                                    fontSize = 16.sp,
                                                    fontWeight = FontWeight.Medium
                                                )
                                                Icon(
                                                    imageVector = Icons.Rounded.KeyboardArrowDown,
                                                    contentDescription = "Mở rộng",
                                                    tint = Color(0xFF7F7FD5)
                                                )
                                            }
                                        }
                                        DropdownMenu(
                                            expanded = showDropdown,
                                            onDismissRequest = { showDropdown = false },
                                            modifier = Modifier
                                                .background(Color.White)
                                                .width(with(LocalDensity.current) {
                                                    (LocalConfiguration.current.screenWidthDp - 72).dp
                                                })
                                        ) {
                                            workoutOptions.forEach { workout ->
                                                DropdownMenuItem(
                                                    text = {
                                                        Text(
                                                            workout,
                                                            fontWeight = FontWeight.Medium
                                                        )
                                                    },
                                                    onClick = {
                                                        selectedWorkout = workout
                                                        showDropdown = false
                                                    },
                                                    leadingIcon = {
                                                        Icon(
                                                            imageVector = when (workout) {
                                                                "Chạy bộ" -> Icons.Rounded.DirectionsRun
                                                                "Yoga" -> Icons.Rounded.SelfImprovement
                                                                "Đạp xe" -> Icons.Rounded.DirectionsBike
                                                                "Bơi lội" -> Icons.Rounded.Pool
                                                                else -> Icons.Rounded.FitnessCenter
                                                            },
                                                            contentDescription = workout,
                                                            tint = Color(0xFF7F7FD5)
                                                        )
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                // Hiển thị đồng hồ đếm ngược
                                /*Box(
                                    modifier = Modifier
                                        .size(140.dp)
                                        .padding(8.dp)
                                        .border(
                                            width = 4.dp,
                                            brush = Brush.sweepGradient(
                                                listOf(
                                                    Color(0xFF7F7FD5),
                                                    Color(0xFF86A8E7),
                                                    Color(0xFF91EAE4),
                                                    Color(0xFF7F7FD5)
                                                )
                                            ),
                                            shape = CircleShape
                                        )
                                        .padding(4.dp)
                                        .background(Color.White, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = "$workoutTime",
                                            fontSize = 36.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (workoutTime <= 10) Color.Red else Color(
                                                0xFF333333
                                            )
                                        )
                                        Text(
                                            text = "giây",
                                            fontSize = 14.sp,
                                            color = Color(0xFF666666)
                                        )
                                    }
                                    // Hiệu ứng đồng hồ đếm ngược
                                    CircularProgressIndicator(
                                        progress = workoutTime / 60f,
                                        modifier = Modifier
                                            .size(140.dp)
                                            .padding(4.dp),
                                        color = if (workoutTime <= 10) Color.Red else Color(
                                            0xFF7F7FD5
                                        ),
                                        strokeWidth = 4.dp
                                    )
                                }

                                LaunchedEffect(isTimerRunning) {
                                    while (isTimerRunning && workoutTime > 0) {
                                        delay(1000L)
                                        workoutTime--
                                    }
                                }

                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    modifier = Modifier.padding(vertical = 8.dp)
                                ) {
                                    OutlinedButton(
                                        onClick = { workoutTime = 60 },
                                        shape = RoundedCornerShape(16.dp),
                                        colors = ButtonDefaults.outlinedButtonColors(
                                            contentColor = Color(0xFF7F7FD5)
                                        ),
                                        border = BorderStroke(1.dp, Color(0xFF7F7FD5)),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Rounded.Refresh,
                                            contentDescription = "Đặt lại",
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Đặt lại", fontWeight = FontWeight.Medium)
                                    }

                                    Button(
                                        onClick = { isTimerRunning = !isTimerRunning },
                                        shape = RoundedCornerShape(16.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (isTimerRunning) Color(0xFFE57373) else Color(
                                                0xFF81C784
                                            )
                                        ),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Icon(
                                            imageVector = if (isTimerRunning) Icons.Rounded.Pause else Icons.Rounded.PlayArrow,
                                            contentDescription = if (isTimerRunning) "Dừng" else "Bắt đầu",
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            if (isTimerRunning) "Dừng" else "Bắt đầu",
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }*/
                                WorkoutTimerScreen()
                            }
                        }
                    }
                }
            }
            // Giao diện Ghi chú sau buổi tập
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Tiêu đề phần ghi chú
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.NoteAlt,
                                contentDescription = "Ghi chú",
                                tint = Color(0xFF7F7FD5),
                                modifier = Modifier.size(28.dp)
                            )
                            Text(
                                "Ghi chú sau buổi tập",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF333333)
                            )
                        }

                        Divider(
                            color = Color(0xFFEEEEEE),
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )

                        // Đánh giá mức độ cường độ
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                "Mức độ cường độ",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF555555)
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Nhẹ nhàng", fontSize = 12.sp, color = Color(0xFF888888))
                                Text("Vừa phải", fontSize = 12.sp, color = Color(0xFF888888))
                                Text("Mạnh mẽ", fontSize = 12.sp, color = Color(0xFF888888))
                            }

                            Slider(
                                value = intensityValue,
                                onValueChange = { intensityValue = it },
                                valueRange = 1f..5f,
                                steps = 3,
                                colors = SliderDefaults.colors(
                                    thumbColor = Color(0xFF7F7FD5),
                                    activeTrackColor = Color(0xFF7F7FD5),
                                    inactiveTrackColor = Color(0xFFE0E0E0)
                                )
                            )
                        }

                        // Đánh giá mức độ mệt mỏi
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                "Mức độ mệt mỏi",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF555555)
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                fatigueOptions.forEachIndexed { index, option ->
                                    OutlinedCard(
                                        modifier = Modifier.weight(1f),
                                        shape = RoundedCornerShape(12.dp),
                                        border = BorderStroke(
                                            width = 1.dp,
                                            color = if (selectedFatigue == index) Color(0xFF7F7FD5) else Color(
                                                0xFFE0E0E0
                                            )
                                        ),
                                        colors = CardDefaults.cardColors(
                                            containerColor = if (selectedFatigue == index) Color(
                                                0xFFF0F0FF
                                            ) else Color.White
                                        )
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable { selectedFatigue = index }
                                                .padding(8.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Text(
                                                text = when (index) {
                                                    0 -> "😊"
                                                    1 -> "😐"
                                                    2 -> "😫"
                                                    else -> "🥵"
                                                },
                                                fontSize = 20.sp
                                            )
                                            Text(
                                                text = option,
                                                fontSize = 11.sp,
                                                textAlign = TextAlign.Center,
                                                color = Color(0xFF555555)
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        // Thời gian luyện tập
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                "Thời gian luyện tập",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF555555)
                            )

                            OutlinedTextField(
                                value = time,
                                onValueChange = { time = it },
                                modifier = Modifier
                                    .fillMaxWidth(),
                                placeholder = { Text("Ghi lại thời gian. VD: 30 phút, 1 giờ...") },
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFF7F7FD5),
                                    unfocusedBorderColor = Color(0xFFE0E0E0),
                                    cursorColor = Color(0xFF7F7FD5)
                                ),
                                textStyle = TextStyle(fontSize = 14.sp),
                            )
                        }

                        // Ghi chú cảm nhận
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                "Cảm nhận & Ghi chú",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF555555)
                            )

                            OutlinedTextField(
                                value = noteText,
                                onValueChange = { noteText = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp),
                                placeholder = { Text("Ghi lại cảm nhận, tiến bộ hoặc mục tiêu tiếp theo của bạn...") },
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFF7F7FD5),
                                    unfocusedBorderColor = Color(0xFFE0E0E0),
                                    cursorColor = Color(0xFF7F7FD5)
                                ),
                                textStyle = TextStyle(fontSize = 14.sp),
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Nút lưu ghi chú
                        Button(
                            onClick = {
                                db.collection("users")
                                    .document(user!!.uid)
                                    .collection("workout")
                                    .add(
                                        hashMapOf(
                                            "workoutType" to selectedWorkout,
                                            "intensityValue" to when (intensityValue) {
                                                1f -> "Nhẹ nhàng"
                                                2f -> "Trung bình nhẹ"
                                                3f -> "Vừa phải"
                                                4f -> "Trung bình cao"
                                                else -> "Mạnh mẽ"
                                            },
                                            "selectedFatigue" to when (selectedFatigue) {
                                                0 -> "Thoải mái"
                                                1 -> "Hơi mệt"
                                                2 -> "Mệt"
                                                3 -> "Kiệt sức"
                                                else -> ""
                                            },
                                            "duration" to time,
                                            "noteText" to noteText,
                                            "timestamp" to System.currentTimeMillis(),
                                        )
                                    )
                                selectedWorkout = "Chọn bài tập"
                                intensityValue = 3f
                                selectedFatigue = 0
                                time = ""
                                noteText = ""
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF7F7FD5)
                            )
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    Icons.Rounded.Save,
                                    contentDescription = "Lưu",
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    "Lưu ghi chú",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }


        }

        // Notification popup
        AnimatedVisibility(
            visible = showNotifications,
            enter = slideInHorizontally(initialOffsetX = { it }) + fadeIn(),
            exit = slideOutHorizontally(targetOffsetX = { it }) + fadeOut(),
            modifier = Modifier
                .align(Alignment.TopEnd)
        ) {
            NotificationsPanel(onDismiss = { showNotifications = false })
        }
    }
}

fun getTime(): String {
    val now = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    return now.format(formatter)
}

private data class Notification(
    val title: String,
    val description: String,
    val icon: String,
    val color: String,
    val timestamp: Timestamp,
    val unread: Boolean = true,
)

@Composable
fun NotificationsPanel(onDismiss: () -> Unit) {
    val db = FirebaseFirestore.getInstance()
    var user by remember { mutableStateOf(Firebase.auth.currentUser) }

    var notifications by remember { mutableStateOf<List<Notification>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        db.collection("users")
            .document(user!!.uid)
            .collection("notification")
            .whereEqualTo("unread", true)
            .get()
            .addOnSuccessListener { documents ->
                val notificationList = mutableListOf<Notification>()
                for (doc in documents) {
                    val data = Notification(
                        doc.getString("title") ?: "",
                        doc.getString("description") ?: "",
                        doc.getString("icon") ?: "",
                        doc.getString("color") ?: "",
                        doc.getTimestamp("timestamp") ?: Timestamp.now(),
                        doc.getBoolean("unread") ?: true
                    )
                    notificationList.add(data)
                    db.collection("users")
                        .document(user!!.uid)
                        .collection("notification")
                        .document(doc.id)
                        .update("unread", false)
                }
                notifications = notificationList
            }
            .addOnFailureListener { e ->
                Log.e("Notifications", "Error loading notifications: ${e.message}")
            }
    }

    Box(
        modifier = Modifier
            .padding(top = 64.dp, end = 16.dp)
            .width(300.dp)
            .fillMaxHeight(0.7f)
            .shadow(10.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.linearGradient(
                    listOf(
                        Color.Black,
                        Color(0xFF333333).copy(alpha = 0.7f)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Thông báo",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.White
                    )
                }
            }

            Divider(
                color = Color.White.copy(alpha = 0.2f),
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                /*ItemNof(
                    title = "Nhắc nhở uống nước",
                    description = "Đã đến giờ uống nước. Hãy uống 250ml nước ngay bây giờ!",
                    time = "5 phút trước",
                    icon = Icons.Default.WaterDrop,
                    color = Color(0xFF03A9F4)
                )

                ItemNof(
                    title = "Mục tiêu hoạt động",
                    description = "Bạn đã đạt được 64% mục tiêu bước đi hôm nay!",
                    time = "30 phút trước",
                    icon = Icons.Default.DirectionsWalk,
                    color = Color(0xFF4CAF50)
                )

                ItemNof(
                    title = "Nhắc nhở tập luyện",
                    description = "Đã đến giờ tập luyện buổi chiều của bạn!",
                    time = "1 giờ trước",
                    icon = Icons.Default.FitnessCenter,
                    color = Color(0xFFFF9800)
                )

                ItemNof(
                    title = "Thành tích mới",
                    description = "Chúc mừng! Bạn đã đạt được kỷ lục cá nhân mới cho bài tập chạy bộ!",
                    time = "3 giờ trước",
                    icon = Icons.Default.EmojiEvents,
                    color = Color(0xFFFFD700)
                )*/
                notifications.forEach { i ->
                    val (hours, minutes) = getElapsedTime(i.timestamp)
                    val timeText = when {
                        hours > 0 -> "$hours giờ trước"
                        minutes > 0 -> "$minutes phút trước"
                        else -> "Vừa xong"
                    }
                    ItemNof(
                        title = i.title,
                        description = i.description,
                        time = timeText,
                        icon = stringToIcon(i.icon),
                        color = hexStringToComposeColor(i.color)
                    )
                }
            }
        }
    }
}

fun stringToIcon(iconName: String): ImageVector {
    return when (iconName) {
        "WaterDrop" -> Icons.Default.WaterDrop
        "Favorite" -> Icons.Default.Favorite
        "MonitorHeart" -> Icons.Default.MonitorHeart
        else -> {Icons.Default.Numbers}
    }
}
fun getElapsedTime(timestamp: Timestamp): Pair<Long, Long> {
    val currentTimeMillis = System.currentTimeMillis()
    val timestampMillis = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000

    val diffMillis = currentTimeMillis - timestampMillis

    val hours = TimeUnit.MILLISECONDS.toHours(diffMillis)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(diffMillis) % 60

    return Pair(hours, minutes)
}
fun hexStringToComposeColor(hexString: String): Color {
    return try {
        // Loại bỏ tiền tố "0x" nếu có
        val cleanHex = hexString.removePrefix("0x")

        // Chuyển đổi thành Long rồi lấy giá trị Int
        val colorInt = cleanHex.toLong(16).toInt()

        // Chuyển thành Color
        Color(colorInt)
    } catch (e: Exception) {
        Log.e("Color", "Invalid color format: $hexString", e)
        Color.Gray // Màu mặc định nếu lỗi
    }
}

@Composable
fun WorkoutTimerScreen() {
    var workoutTime by remember { mutableStateOf(60) }
    var selectedMinutes by remember { mutableStateOf(1) }
    var isTimerRunning by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    if (showTimePicker) {
        /*TimePickerDialog(
            LocalContext.current,
            { _, hour, minute ->
                selectedMinutes = hour * 60 + minute
                workoutTime = selectedMinutes * 60
                showTimePicker = false
            },
            selectedMinutes / 60,
            selectedMinutes % 60,
            true
        ).show()*/
        Dialog(
            onDismissRequest = { showTimePicker = false }
        ) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surface
            ) {
                CustomMinutePicker(
                    selectedMinutes = selectedMinutes,
                    onMinutesSelected = { newMinutes ->
                        selectedMinutes = newMinutes
                        workoutTime = selectedMinutes * 60
                        showTimePicker = false  // Đóng dialog sau khi xác nhận
                    }
                )
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "THỜI GIAN TẬP LUYỆN",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF7F7FD5),
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Button(
            onClick = { showTimePicker = true },
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7F7FD5)),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(50.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Timer,
                    contentDescription = "Timer",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Chọn thời gian: ${selectedMinutes} phút",
                    fontSize = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Improved timer display
        Box(
            modifier = Modifier
                .size(200.dp)
                .shadow(8.dp, CircleShape)
                .background(Color.White, CircleShape)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            // Background circle
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF5F5F5), CircleShape)
            )

            // Progress indicator
            CircularProgressIndicator(
                progress = workoutTime / (selectedMinutes * 60f),
                modifier = Modifier.fillMaxSize(),
                color = when {
                    workoutTime <= 10 -> Color(0xFFE53935)
                    workoutTime <= 30 -> Color(0xFFFFA000)
                    else -> Color(0xFF7F7FD5)
                },
                strokeWidth = 12.dp
            )

            // Time display
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                val minutes = workoutTime / 60
                val seconds = workoutTime % 60

                Text(
                    text = String.format("%02d:%02d", minutes, seconds),
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = when {
                        workoutTime <= 10 -> Color(0xFFE53935)
                        workoutTime <= 30 -> Color(0xFFFFA000)
                        else -> Color(0xFF7F7FD5)
                    }
                )

                Text(
                    text = if (isTimerRunning) "Đang chạy" else "Sẵn sàng",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
        }

        LaunchedEffect(isTimerRunning) {
            while (isTimerRunning && workoutTime > 0) {
                delay(1000L)
                workoutTime--
            }
            if (workoutTime == 0 && isTimerRunning) {
                isTimerRunning = false
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            OutlinedButton(
                onClick = {
                    workoutTime = selectedMinutes * 60
                    isTimerRunning = false
                },
                shape = RoundedCornerShape(24.dp),
                border = BorderStroke(2.dp, Color.Gray),
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = "Reset",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Đặt lại")
            }

            Button(
                onClick = { isTimerRunning = !isTimerRunning },
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isTimerRunning) Color(0xFFE53935) else Color(0xFF4CAF50)
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
            ) {
                Icon(
                    imageVector = if (isTimerRunning) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                    contentDescription = if (isTimerRunning) "Pause" else "Play",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(if (isTimerRunning) "Dừng" else "Bắt đầu")
            }
        }
    }
}

@Composable
fun CustomMinutePicker(
    selectedMinutes: Int,
    onMinutesSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val maxMinutes = 120
    val minMinutes = 1

    // State cho số phút được chọn (giá trị tạm thời)
    var tempMinutes by remember { mutableStateOf(selectedMinutes) }

    // Hàm để áp dụng giới hạn giá trị
    fun updateMinutes(newValue: Int) {
        tempMinutes = newValue.coerceIn(minMinutes, maxMinutes)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Thời gian tập luyện",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Hiển thị số phút được chọn
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$tempMinutes",
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            text = "phút",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Bộ điều khiển tăng/giảm
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Nút giảm nhanh (-10)
            FilledIconButton(
                onClick = { updateMinutes(tempMinutes - 10) },
                enabled = tempMinutes > minMinutes + 9
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardDoubleArrowLeft,
                    contentDescription = "Giảm 10 phút"
                )
            }

            // Nút giảm (-1)
            FilledIconButton(
                onClick = { updateMinutes(tempMinutes - 1) },
                enabled = tempMinutes > minMinutes
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "Giảm 1 phút"
                )
            }

            // Nút tăng (+1)
            FilledIconButton(
                onClick = { updateMinutes(tempMinutes + 1) },
                enabled = tempMinutes < maxMinutes
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Tăng 1 phút"
                )
            }

            // Nút tăng nhanh (+10)
            FilledIconButton(
                onClick = { updateMinutes(tempMinutes + 10) },
                enabled = tempMinutes < maxMinutes - 9
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardDoubleArrowRight,
                    contentDescription = "Tăng 10 phút"
                )
            }
        }

        // Thanh trượt phụ để điều chỉnh nhanh
        Slider(
            value = tempMinutes.toFloat(),
            onValueChange = { updateMinutes(it.toInt()) },
            valueRange = minMinutes.toFloat()..maxMinutes.toFloat(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp)
        )

        // Các giá trị phổ biến
        Text(
            text = "Thời gian phổ biến",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Các nút shortcut cho các giá trị phổ biến
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            items(listOf(5, 10, 15, 20, 30, 45, 60)) { value ->
                SuggestionChip(
                    onClick = { updateMinutes(value) },
                    label = { Text("$value") }
                )
            }
        }

        // Các nút hủy và xác nhận
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = { onMinutesSelected(selectedMinutes) }, // Trả về giá trị cũ và đóng dialog
                modifier = Modifier.weight(1f)
            ) {
                Text("Hủy")
            }

            Button(
                onClick = { onMinutesSelected(tempMinutes) }, // Áp dụng giá trị mới
                modifier = Modifier.weight(1f)
            ) {
                Text("Xác nhận")
            }
        }
    }
}