package com.example.healthtrackingapp

import android.Manifest
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresPermission
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthtrackingapp.R
import com.example.healthtrackingapp.ui.components.SwipeToAcceptButtonn
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AlarmActivity : ComponentActivity() {
    private var mediaPlayer: MediaPlayer? = null
    private var vibrator: Vibrator? = null
    private var isVibrating = false

    @RequiresPermission(Manifest.permission.VIBRATE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Cấu hình để hiển thị trên màn hình khóa
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        } else {
            window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
            )
        }

        // Nhận thông tin báo thức từ Intent
        val alarmId = intent.getLongExtra("alarm_id", -1)
        val alarmTitle = intent.getStringExtra("alarm_title") ?: "Báo thức"

        // Bắt đầu âm báo thức
        startAlarmSound()

        // Bắt đầu rung
        startVibration()

        setContent {
            MaterialTheme {
                AlarmScreen(
                    alarmTitle = alarmTitle,
                    currentTime = getCurrentTime(),
                    onDismiss = { dismissAlarm() },
//                    onSnooze = { snoozeAlarm(alarmId) }
                )
            }
        }
    }

    private fun getCurrentTime(): String {
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return dateFormat.format(Date())
    }

    private fun startAlarmSound() {
        try {
            mediaPlayer = MediaPlayer.create(this, R.raw.iphone_sound)
            mediaPlayer?.isLooping = true
            mediaPlayer?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @RequiresPermission(Manifest.permission.VIBRATE)
    private fun startVibration() {
        vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val vibrationEffect = VibrationEffect.createWaveform(
                longArrayOf(0, 500, 500), // Thời gian đợi, rung, đợi...
                0 // Lặp lại từ chỉ mục 0
            )
            vibrator?.vibrate(vibrationEffect)
        } else {
            vibrator?.vibrate(longArrayOf(0, 500, 500), 0)
        }

        isVibrating = true
    }

    private fun stopAlarmSound() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    @RequiresPermission(Manifest.permission.VIBRATE)
    private fun stopVibration() {
        if (isVibrating) {
            vibrator?.cancel()
            isVibrating = false
        }
    }

    @RequiresPermission(Manifest.permission.VIBRATE)
    private fun dismissAlarm() {
        stopAlarmSound()
        stopVibration()

        // Cập nhật trạng thái báo thức trong DB
        val alarmId = intent.getLongExtra("alarm_id", -1)
        if (alarmId.toInt() != -1) {
            // Gọi hàm cập nhật trong database
            // AlarmDatabase.getInstance(this).updateAlarmState(alarmId, false)
        }

        finish()
    }

    /*@RequiresPermission(Manifest.permission.VIBRATE)
    private fun snoozeAlarm(alarmId: Long) {
        stopAlarmSound()
        stopVibration()

        // Lên lịch lại báo thức sau 5 phút
        val snoozeIntent = Intent(this, AlarmSchedulerService::class.java)
        snoozeIntent.putExtra("alarm_id", alarmId)
        snoozeIntent.putExtra("snooze_minutes", 5)
        snoozeIntent.action = "SNOOZE_ALARM"
        startService(snoozeIntent)

        finish()
    }*/

    @RequiresPermission(Manifest.permission.VIBRATE)
    override fun onDestroy() {
        stopAlarmSound()
        stopVibration()
        super.onDestroy()
    }
}

@Composable
fun AlarmScreen(
    alarmTitle: String,
    currentTime: String,
    onDismiss: () -> Unit,
//    onSnooze: () -> Unit
) {
    val backgroundColor = Color.Transparent
    val primaryColor = Color(0xFF4CAF50)

    // Hiệu ứng nhấp nháy
    val pulseAnimation = rememberInfiniteTransition()
    val alpha by pulseAnimation.animateFloat(
        initialValue = 0.7f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg_alarm),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // Icon báo thức
            Icon(
                imageVector = Icons.Default.Alarm,
                contentDescription = "Alarm Icon",
                tint = primaryColor.copy(alpha = alpha),
                modifier = Modifier.size(80.dp)
            )

            // Thời gian hiện tại
            Text(
                text = currentTime,
                color = Color.White,
                fontSize = 70.sp,
                fontWeight = FontWeight.Bold
            )

            // Tiêu đề báo thức
            Text(
                text = alarmTitle,
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(60.dp))

            // Nút báo lại
            /*Button(
                onClick = { onSnooze() },
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = "Báo lại sau 5 phút",
                    fontSize = 16.sp
                )
            }*/

            Spacer(modifier = Modifier.height(16.dp))

            // SwipeToAccept button
            SwipeToAcceptButton(
                onSwiped = { onDismiss() },
                text = "Trượt để tắt báo thức",
                backgroundColor = primaryColor
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun SwipeToAcceptButton(
    onSwiped: () -> Unit,
    text: String = "Trượt để tắt báo thức",
    backgroundColor: Color = Color(0xFF4CAF50),
    textColor: Color = Color.White,
    height: Dp = 56.dp
) {
    val width = 340.dp
    val thumbSize = height - 16.dp

    var offsetX by remember { mutableStateOf(0f) }
    val maxOffset = width - thumbSize - 16.dp
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val density = LocalDensity.current.density

    Box(
        modifier = Modifier
            .width(width)
            .height(height)
            .clip(RoundedCornerShape(height / 2))
            .background(backgroundColor)
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragEnd = {
                        if (offsetX > maxOffset.toPx() * 0.9f) {
                            // Đủ xa để kích hoạt
                            coroutineScope.launch {
                                delay(200) // Đợi hiệu ứng hoàn thành
                                onSwiped()
                            }
                        } else {
                            // Không đủ xa, quay lại vị trí ban đầu
                            offsetX = 0f
                        }
                    },
                    onDragCancel = {
                        offsetX = 0f
                    },
                    onHorizontalDrag = { change, dragAmount ->
                        val newValue = (offsetX + dragAmount).coerceIn(0f, maxOffset.toPx())
                        offsetX = newValue
                        change.consume()
                    }
                )
            },
        contentAlignment = Alignment.CenterStart
    ) {
        // Text hướng dẫn
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = text,
                color = textColor,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }

        // Thumb (nút trượt)
        Box(
            modifier = Modifier
                .padding(start = 8.dp + (offsetX / density).dp)
                .size(thumbSize)
                .clip(CircleShape)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Swipe",
                tint = backgroundColor
            )
        }
    }
}