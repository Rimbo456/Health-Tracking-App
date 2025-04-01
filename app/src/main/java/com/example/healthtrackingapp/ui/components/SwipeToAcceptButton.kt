package com.example.healthtrackingapp.ui.components

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

@Composable
fun SwipeToAcceptButtonn(onSwiped: () -> Unit) {
    var offsetX by remember { mutableStateOf(0f) }
    val maxOffset = 600f  // Giới hạn vuốt
    val animatedOffset by animateFloatAsState(targetValue = offsetX, label = "")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(Color.Gray, shape = RoundedCornerShape(50.dp))
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = "Vuốt để tắt báo thức",
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )

        Box(
            modifier = Modifier
                .offset { IntOffset(animatedOffset.toInt(), 0) }
                .size(80.dp)
                .background(Color.Green, shape = CircleShape)
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            if (offsetX > maxOffset * 0.7f) {
                                onSwiped()  // Thực hiện hành động khi vuốt đủ xa
                            }
                            offsetX = 0f  // Đưa nút về vị trí ban đầu
                        }
                    ) { _, dragAmount ->
                        offsetX = (offsetX + dragAmount).coerceIn(0f, maxOffset)
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Alarm, contentDescription = "Nhận cuộc gọi", tint = Color.White)
        }
    }
}
