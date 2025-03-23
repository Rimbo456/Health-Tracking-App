package com.example.healthtrackingapp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Refresh
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.example.healthtrackingapp.R
import com.example.healthtrackingapp.ui.components.Calendar
import com.example.healthtrackingapp.ui.components.ItemNof
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun NotificationScreen() {
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
                                Box(
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
                                }
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
                                                fontSize = 12.sp,
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

@Composable
fun NotificationsPanel(onDismiss: () -> Unit) {
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
                ItemNof(
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
                )
            }
        }
    }
}
