package com.example.healthtrackingapp.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.material.icons.filled.SentimentNeutral
import androidx.compose.material.icons.filled.SentimentSatisfied
import androidx.compose.material.icons.filled.SentimentVeryDissatisfied
import androidx.compose.material.icons.filled.SentimentVerySatisfied
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class SymptomData(
    val description: String = "",
    val severity: Float = 3f,
    val time: String = "",
    val medications: List<String> = emptyList()
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SymptomEntryDialog(
    onDismiss: () -> Unit,
    onSubmit: (SymptomData) -> Unit
) {
    var symptomData by remember { mutableStateOf(SymptomData()) }
    var currentStep by remember { mutableStateOf(0) }
    val totalSteps = 4

    var newMedication by remember { mutableStateOf("") }
    var showTimeOptions by remember { mutableStateOf(false) }
    var customTime by remember { mutableStateOf("") }

    val timeOptions = listOf(
        "Vừa mới",
        "Hôm nay",
        "Hôm qua",
        "Tuần trước",
        "Tháng trước",
        "Khác"
    )

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFF8F9FA),
                                Color(0xFFF1F3F5)
                            )
                        )
                    )
                    .padding(24.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Nhập Triệu Chứng",
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2D5BA8)
                        )
                    )
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(32.dp)
                            .background(Color(0xFFE9ECEF), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Đóng",
                            tint = Color(0xFF495057)
                        )
                    }
                }

                // Progress indicator
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    (0 until totalSteps).forEach { step ->
                        val isActive = step <= currentStep
                        val animatedScale by animateFloatAsState(
                            targetValue = if (step == currentStep) 1.2f else 1f,
                            animationSpec = tween(300)
                        )

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(4.dp)
                                .padding(horizontal = 2.dp)
                                .background(
                                    if (isActive) {
                                        when (step) {
                                            0 -> Color(0xFF2D5BA8)
                                            1 -> Color(0xFF2D78A8)
                                            2 -> Color(0xFF2D96A8)
                                            else -> Color(0xFF2DA8A1)
                                        }
                                    } else {
                                        Color(0xFFCED4DA)
                                    }
                                )
                                .scale(if (step == currentStep) animatedScale else 1f)
                        )
                    }
                }

                Text(
                    text = "Bước ${currentStep + 1}/$totalSteps",
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = Color(0xFF6C757D)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    textAlign = TextAlign.Right
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Content based on current step
                AnimatedVisibility(
                    visible = currentStep == 0,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    // Step 1: Description
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = Color(0xFF2D5BA8),
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Mô tả triệu chứng",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF343A40)
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = symptomData.description,
                            onValueChange = { symptomData = symptomData.copy(description = it) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            placeholder = { Text("Vui lòng mô tả chi tiết triệu chứng bạn đang gặp phải...") },
                            colors = TextFieldDefaults.colors(
                                Color(0xFF2D5BA8),
                                Color(0xFFADB5BD)
                            ),
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }
                }

                AnimatedVisibility(
                    visible = currentStep == 1,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    // Step 2: Severity
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.SentimentDissatisfied,
                                contentDescription = null,
                                tint = Color(0xFF2D78A8),
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Mức độ nghiêm trọng",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF343A40)
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            val icon = when {
                                symptomData.severity < 2 -> Icons.Default.SentimentVerySatisfied
                                symptomData.severity < 3 -> Icons.Default.SentimentSatisfied
                                symptomData.severity < 4 -> Icons.Default.SentimentNeutral
                                symptomData.severity < 5 -> Icons.Default.SentimentDissatisfied
                                else -> Icons.Default.SentimentVeryDissatisfied
                            }

                            val color = when {
                                symptomData.severity < 2 -> Color(0xFF40C057)
                                symptomData.severity < 3 -> Color(0xFF82C91E)
                                symptomData.severity < 4 -> Color(0xFFFCC419)
                                symptomData.severity < 5 -> Color(0xFFF76707)
                                else -> Color(0xFFF03E3E)
                            }

                            val description = when {
                                symptomData.severity < 2 -> "Rất nhẹ"
                                symptomData.severity < 3 -> "Nhẹ"
                                symptomData.severity < 4 -> "Trung bình"
                                symptomData.severity < 5 -> "Nặng"
                                else -> "Rất nặng"
                            }

                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                tint = color,
                                modifier = Modifier.size(64.dp)
                            )

                            Text(
                                text = description,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = color
                                ),
                                modifier = Modifier.padding(top = 80.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Slider(
                            value = symptomData.severity,
                            onValueChange = { symptomData = symptomData.copy(severity = it) },
                            valueRange = 1f..5f,
                            steps = 3,
                            colors = SliderDefaults.colors(
                                thumbColor = Color(0xFF2D78A8),
                                activeTrackColor = Color(0xFF2D78A8),
                                inactiveTrackColor = Color(0xFFE9ECEF)
                            ),
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("1", color = Color(0xFF6C757D))
                            Text("2", color = Color(0xFF6C757D))
                            Text("3", color = Color(0xFF6C757D))
                            Text("4", color = Color(0xFF6C757D))
                            Text("5", color = Color(0xFF6C757D))
                        }
                    }
                }

                AnimatedVisibility(
                    visible = currentStep == 2,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    // Step 3: Time
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AccessTime,
                                contentDescription = null,
                                tint = Color(0xFF2D96A8),
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Thời gian xuất hiện",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF343A40)
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Box(modifier = Modifier.fillMaxWidth()) {
                            OutlinedTextField(
                                value = symptomData.time,
                                onValueChange = { symptomData = symptomData.copy(time = it) },
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = { Text("Chọn thời gian xuất hiện triệu chứng") },
                                colors = TextFieldDefaults.colors(
                                    Color(0xFF2D96A8),
                                    Color(0xFFADB5BD)
                                ),
                                trailingIcon = {
                                    IconButton(onClick = { showTimeOptions = !showTimeOptions }) {
                                        Icon(
                                            imageVector = Icons.Default.AccessTime,
                                            contentDescription = "Chọn thời gian",
                                            tint = Color(0xFF2D96A8)
                                        )
                                    }
                                },
                                shape = RoundedCornerShape(12.dp),
                                readOnly = true
                            )

                            DropdownMenu(
                                expanded = showTimeOptions,
                                onDismissRequest = { showTimeOptions = false },
                                modifier = Modifier.fillMaxWidth(0.9f)
                            ) {
                                timeOptions.forEach { option ->
                                    DropdownMenuItem(
                                        onClick = {
                                            symptomData = symptomData.copy(time = option)
                                            showTimeOptions = false
                                        },
                                        text = { Text(option) },
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        if (symptomData.time == "Khác") {
                            OutlinedTextField(
                                value = symptomData.time,
                                onValueChange = { symptomData = symptomData.copy(time = it) },
                                modifier = Modifier.fillMaxWidth(),
                                label = { Text("Thời gian cụ thể") },
                                colors = TextFieldDefaults.colors(
                                    Color(0xFF2D96A8),
                                    Color(0xFFADB5BD)
                                ),
                                shape = RoundedCornerShape(12.dp)
                            )
                        }
                    }
                }

                AnimatedVisibility(
                    visible = currentStep == 3,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    // Step 4: Medications
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Medication,
                                contentDescription = null,
                                tint = Color(0xFF2DA8A1),
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Thuốc đã sử dụng",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF343A40)
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = newMedication,
                                onValueChange = { newMedication = it },
                                modifier = Modifier.weight(1f),
                                placeholder = { Text("Nhập tên thuốc") },
                                colors = TextFieldDefaults.colors(
                                    Color(0xFF2DA8A1),
                                    Color(0xFFADB5BD)
                                ),
                                shape = RoundedCornerShape(12.dp),
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Done
                                )
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Button(
                                onClick = {
                                    if (newMedication.isNotBlank()) {
                                        symptomData = symptomData.copy(
                                            medications = symptomData.medications + newMedication
                                        )
                                        newMedication = ""
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    Color(0xFF2DA8A1)
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("Thêm")
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text(
                                text = if (symptomData.medications.isEmpty())
                                    "Chưa có thuốc nào được thêm"
                                else
                                    "Danh sách thuốc đã sử dụng:",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    color = Color(0xFF6C757D)
                                )
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                symptomData.medications.forEach { medication ->
                                    AssistChip(
                                        onClick = {
                                            symptomData = symptomData.copy(
                                                medications = symptomData.medications - medication
                                            )
                                        },
                                        colors = AssistChipDefaults.assistChipColors(
                                            Color(0xFFE3F2FD)
                                        ),
                                        label = {
                                            Icon(
                                                Icons.Default.Medication,
                                                contentDescription = null,
                                                tint = Color(0xFF2DA8A1),
                                                modifier = Modifier.size(16.dp)
                                            )
                                            Text(medication)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Navigation buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            if (currentStep > 0) {
                                currentStep--
                            }
                        },
                        enabled = currentStep > 0,
                        colors = ButtonDefaults.buttonColors(
                            Color(0xFFE9ECEF),
                            Color(0xFF495057),
                            Color(0xFFF8F9FA),
                            Color(0xFFADB5BD)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Quay lại")
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Button(
                        onClick = {
                            if (currentStep < totalSteps - 1) {
                                currentStep++
                            } else {
                                onSubmit(symptomData)
                            }
                        },
                        enabled = when (currentStep) {
                            0 -> symptomData.description.isNotBlank()
                            1 -> true
                            2 -> symptomData.time.isNotBlank()
                            else -> true
                        },
                        colors = ButtonDefaults.buttonColors(
                            Color(0xFF2D5BA8),
                            Color.White,
                            Color(0xFFADB5BD),
                            Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(if (currentStep < totalSteps - 1) "Tiếp tục" else "Hoàn thành")
                    }
                }
            }
        }
    }
}

@Composable
private fun SliderDefaults.colors(
    thumbColor: Color,
    activeTrackColor: Color,
    inactiveTrackColor: Color
) = SliderDefaults.colors(
    thumbColor = thumbColor,
    activeTrackColor = activeTrackColor,
    inactiveTrackColor = inactiveTrackColor
)

// Sử dụng trong ứng dụng
@Composable
fun SymptomEntryExample() {
    var showDialog by remember { mutableStateOf(false) }
    var lastSymptomData by remember { mutableStateOf<SymptomData?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { showDialog = true },
            colors = ButtonDefaults.buttonColors(
                Color(0xFF2D5BA8)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Nhập triệu chứng", color = Color.White)
        }

        if (showDialog) {
            SymptomEntryDialog(
                onDismiss = { showDialog = false },
                onSubmit = { symptomData ->
                    lastSymptomData = symptomData
                    showDialog = false
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        lastSymptomData?.let { data ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Thông tin triệu chứng",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF343A40)
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Mô tả: ${data.description}",
                        style = TextStyle(fontSize = 14.sp)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Mức độ: ${data.severity} - ${
                            when {
                                data.severity < 2 -> "Rất nhẹ"
                                data.severity < 3 -> "Nhẹ"
                                data.severity < 4 -> "Trung bình"
                                data.severity < 5 -> "Nặng"
                                else -> "Rất nặng"
                            }
                        }",
                        style = TextStyle(fontSize = 14.sp)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Thời gian: ${data.time}",
                        style = TextStyle(fontSize = 14.sp)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Thuốc đã dùng: ${data.medications.joinToString(", ")}",
                        style = TextStyle(fontSize = 14.sp)
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun SymptomEntryExamplePreview() {
    SymptomEntryExample()
}