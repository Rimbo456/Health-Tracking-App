/*
package com.example.healthtrackingapp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.healthtrackingapp.R
import com.example.healthtrackingapp.ui.components.BodyTemperatureChart
import com.example.healthtrackingapp.ui.components.CircularCheckboxWithIcon
import com.example.healthtrackingapp.ui.components.SymptomData
import com.example.healthtrackingapp.ui.components.SymptomEntryDialog
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun OverviewScreen(
    navController: NavHostController
) {
    var ghichu by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var lastSymptomData by remember { mutableStateOf<SymptomData?>(null) }

    Scaffold(
        modifier = Modifier.padding(WindowInsets.systemBars.asPaddingValues()),
        topBar = { TopBarDate(navController) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Image(
                painter = painterResource(id = R.drawable.nen_app),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.9f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Daily Health Notes",
                    fontSize = 29.sp,
                    fontWeight = FontWeight.Bold
                )
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .padding(vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "Triệu chứng",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.SemiBold,
                            )
                            IconButton(
                                onClick = { showDialog = true },

                                ) {
                                Icon(
                                    imageVector = Icons.Default.AddCircle,
                                    contentDescription = null,
                                    tint = Color.Black,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        }
                        Box(
                            modifier = Modifier
                                .height(2.dp)
                                .fillMaxWidth(0.9f)
                                .background(Color.Gray)
                        )
                        lastSymptomData?.let { data ->
                            ExpandableCard(
                                title = "Trieu chung 1",
                                width = 0.5f,
                                content = {
                                    Column(
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = buildAnnotatedString {
                                                withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                                    append("Mô tả triệu chứng: ")
                                                }
                                                append(data.description)
                                            }
                                        )
                                        Text(
                                            text = buildAnnotatedString {
                                                withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                                    append("Thời gian xuất hiện triệu chứng: ")
                                                }
                                                append(data.time)
                                            }
                                        )
                                        Text(
                                            text = buildAnnotatedString {
                                                withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                                    append("Mức độ nặng nhẹ: ")
                                                }
                                                append(
                                                    "${data.severity} - ${
                                                        when {
                                                            data.severity < 2 -> "Rất nhẹ"
                                                            data.severity < 3 -> "Nhẹ"
                                                            data.severity < 4 -> "Trung bình"
                                                            data.severity < 5 -> "Nặng"
                                                            else -> "Rất nặng"
                                                        }
                                                    }"
                                                )
                                            }
                                        )
                                        Text(
                                            text = buildAnnotatedString {
                                                withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                                    append("Hướng xử lý: ")
                                                }
                                                append("")
                                            }
                                        )
                                        Text(
                                            text = buildAnnotatedString {
                                                withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                                    append("Thuốc đã dùng: ")
                                                }
                                                append(data.medications.joinToString(", "))
                                            }
                                        )
                                    }
                                }
                            )
                            Box(
                                modifier = Modifier
                                    .height(1.dp)
                                    .fillMaxWidth(0.7f)
                                    .background(Color.Gray)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .height(2.dp)
                                .fillMaxWidth(0.9f)
                                .background(Color.Gray)
                        )
                        ExpandableCard(
                            title = "Nhiet do: 37.5 độ C",
                            width = 1f,
                            content = {
                                BodyTemperatureChart(LocalDate.now().dayOfMonth)
                            }
                        )
                    }
                }
                */
/*ExpandableCard(
                    title = stringResource(id = R.string.nuandiet),
                    content = {
                        Text(
                            text = "Bữa ăn trong ngày:",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.weight(0.3f)
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(text = "Sáng: ")
//                                    //CircularCheckboxWithIcon()
                                }
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(text = "Phụ sáng: ")
                                    //CircularCheckboxWithIcon()
                                }
                            }
                            Column(
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.weight(0.3f)
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(text = "Trưa: ")
                                    //CircularCheckboxWithIcon()
                                }
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(text = "Phụ trưa: ")
                                    //CircularCheckboxWithIcon()
                                }
                            }
                            Column(
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.weight(0.3f)
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(text = "Chiều: ")
                                    //CircularCheckboxWithIcon()
                                }
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(text = "Phụ chiều: ")
                                    //CircularCheckboxWithIcon()
                                }
                            }
                        }
                        Text(
                            text = buildAnnotatedString { 
                                withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                    append("Lượng nước đã uống: ")
                                }
                                append("2 tỷ lít")
                            }
                        )
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                    append("Thực phẩm đã tiêu thụ: ")
                                }
                                append("Mì tôm")
                            }
                        )
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                    append("Vitamin/sữa/thuốc bổ (nếu có): ")
                                }
                                append("Không có, nhưng có chơi đồ")
                            }
                        )
                    },
                    width = 1f
                )
                ExpandableCard(
                    title = stringResource(id = R.string.phyacaworkout),
                    content = {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                    append("Loại hình tập luyện: ")
                                }
                                append("Thể thao điện tử")
                            }
                        )
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                    append("Thời gian tập luyện: ")
                                }
                                append("Cả đời")
                            }
                        )
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                    append("Cường độ tập luyện: ")
                                }
                                append("Liên tục không nghỉ")
                            }
                        )
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                    append("Số bước đi được trong ngày: ")
                                }
                                append("")
                            }
                        )
                    },
                    width = 1f
                )
                ExpandableCard(
                    title = stringResource(id = R.string.moodaemo),
                    content = {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                    append("Cảm xúc trong ngày: ")
                                }
                                append("")
                            }
                        )
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                    append("Nguyên nhân có thể ảnh hưởng đến tâm trạng: ")
                                }
                                append("")
                            }
                        )
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                    append("Ghi chú cá nhân (Nếu có điều gì đặc biệt xảy ra trong ngày): ")
                                }
                                append("")
                            }
                        )
                    },
                    width = 1f
                )*//*

            }
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
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBarDate(navController: NavHostController) {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.08f)
            .background(color = Color.White, shape = RoundedCornerShape(20.dp)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth(0.95f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.6f)
            ) {
                IconButton(onClick = { selectedDate = selectedDate.minusDays(1) }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Ngày trước")
                }
                Card {
                    Text(
                        text = selectedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
                IconButton(
                    onClick = { selectedDate = selectedDate.plusDays(1) },
                    enabled = if (selectedDate.dayOfMonth == LocalDate.now().dayOfMonth) false else true
                ) {
                    Icon(Icons.Default.ArrowForward, contentDescription = "Ngày sau")
                }

            }
            Box(
                modifier = Modifier.weight(0.4f),
                contentAlignment = Alignment.CenterEnd
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    colors = IconButtonDefaults.iconButtonColors(Color.Black),
                    modifier = Modifier.padding(start = 10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun ExpandableCard(title: String, content: @Composable () -> Unit, width: Float) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { expanded = !expanded },
//        colors = CardDefaults.cardColors(Color.LightGray),
        shape = RoundedCornerShape(10.dp),
//        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(width),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Icon(
                    imageVector = if (expanded) {
                        Icons.Default.ExpandLess
                    } else {
                        Icons.Default.ExpandMore
                    }, contentDescription = null
                )
            }

            AnimatedVisibility(
                visible = expanded,
            ) {
                Column(
                    modifier = Modifier.padding(top = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    */
/*Box(
                        modifier = Modifier
                            .height(1.dp)
                            .fillMaxWidth()
                            .background(Color.Gray)
                    )*//*

                    content()
                }
            }
        }
    }
}*/

package com.example.healthtrackingapp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.healthtrackingapp.R
import com.example.healthtrackingapp.ui.components.BloodGlucoseChart
import com.example.healthtrackingapp.ui.components.BloodOxygenLevelChart
import com.example.healthtrackingapp.ui.components.BodyTemperatureChart
import com.example.healthtrackingapp.ui.components.ExpandCard
import com.example.healthtrackingapp.ui.components.HeartRateChart
import com.example.healthtrackingapp.ui.components.JetpackComposeBasicLineChart
import com.example.healthtrackingapp.ui.components.SymptomData
import com.example.healthtrackingapp.ui.components.SymptomEntryDialog
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// Define theme colors for consistent style
private val primaryColor = Color(0xFF4E7DFF)
private val secondaryColor = Color(0xFFFF7D7D)
private val backgroundColor = Color(0xFFF5F7FF)
private val cardBackgroundColor = Color.White
private val textPrimaryColor = Color(0xFF2D3142)
private val textSecondaryColor = Color(0xFF9095A7)
private val accentGradient = Brush.linearGradient(
    colors = listOf(Color(0xFF4E7DFF), Color(0xFF56CCF2))
)

@Composable
fun OverviewScreen(
    navController: NavHostController
) {
    var showDialog by remember { mutableStateOf(false) }
    var lastSymptomData by remember { mutableStateOf<SymptomData?>(null) }

    Scaffold(
        modifier = Modifier.padding(WindowInsets.systemBars.asPaddingValues()),
        topBar = { TopBarDate(navController) },
        containerColor = backgroundColor
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Subtle background image with overlay gradient for better readability
            Image(
                painter = painterResource(id = R.drawable.nen_app),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )

            // Main content column
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header with fancy gradient
                HeaderSection()

                // Card for Symptoms
                SymptomsSection(
                    lastSymptomData = lastSymptomData,
                    onAddSymptom = { showDialog = true }
                )

                ExpandableContainer(
                    title = "Biểu đồ chỉ số cơ thể",
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Card for Temperature
                        ExpandCard(
                            title = "Nhiệt độ cơ thể",
                            value = 37.5,
                            unit = "°C",
                            colorEle = Color(red = 218, green = 115, blue = 35, alpha = 255),
                            content = {
                                BodyTemperatureChart(LocalDate.now().dayOfMonth)
                            }
                        )

                        // Card for Blood Presure
                        ExpandCard(
                            title = "Huyết áp",
                            value = 117.0,
                            unit = "mmHg",
                            colorEle = Color(red = 255, green = 32, blue = 32, alpha = 255),
                            content = {
                                JetpackComposeBasicLineChart()
                            }
                        )

                        // Heart Rate
                        ExpandCard(
                            title = "Bắn tym",
                            value = 80.0,
                            unit = "BPM",
                            colorEle = Color(red = 255, green = 32, blue = 32, alpha = 255),
                            content = {
                                HeartRateChart()
                            }
                        )

                        // blood glucose
                        ExpandCard(
                            title = "Đường huyết",
                            value = 140.0,
                            unit = "mg/dL",
                            colorEle = Color(red = 177, green = 255, blue = 32, alpha = 255),
                            content = {
                                BloodGlucoseChart()
                            }
                        )

                        //blood oxygen levels
                        ExpandCard(
                            title = "Nồng độ oxygen trong máu",
                            value = 95.0,
                            unit = "%",
                            colorEle = Color(red = 32, green = 229, blue = 255, alpha = 255),
                            content = {
                                BloodOxygenLevelChart()
                            }
                        )
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Tiêu đề
                        Text(
                            text = "Bữa ăn trong ngày",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Divider(thickness = 1.dp, color = Color.Black)

                        // Khu vực bữa ăn
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            MealColumn(
                                title = "Buổi sáng",
                                meals = listOf("Chính", "Phụ"),
                                modifier = Modifier.weight(1f)
                            )

                            VerticalDivider(
                                thickness = 1.dp,
                                color = Color.Black
                            )

                            MealColumn(
                                title = "Buổi trưa",
                                meals = listOf("Chính", "Phụ"),
                                modifier = Modifier.weight(1f)
                            )

                            VerticalDivider(
                                thickness = 1.dp,
                                color = MaterialTheme.colorScheme.surfaceVariant
                            )

                            MealColumn(
                                title = "Buổi chiều",
                                meals = listOf("Chính", "Phụ"),
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.surfaceVariant)

                        // Thông tin dinh dưỡng
                        NutritionInfoItem(
                            label = "Lượng nước đã uống",
                            value = "2 lít",
                            icon = Icons.Filled.Check
                        )

                        NutritionInfoItem(
                            label = "Thực phẩm đã tiêu thụ",
                            value = "Mì tôm",
                            icon = Icons.Filled.Check
                        )

                        NutritionInfoItem(
                            label = "Vitamin/sữa/thuốc bổ",
                            value = "Không có",
                            icon = null
                        )
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Tiêu đề
                        Text(
                            text = "Vận động và tập luyện",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Divider(thickness = 1.dp, color = Color.Black)


                        // Thông tin dinh dưỡng
                        NutritionInfoItem(
                            label = "Loại hình tập luyện",
                            value = "Yoga",
                            icon = Icons.Filled.Check
                        )

                        NutritionInfoItem(
                            label = "Thời gian tập luyện",
                            value = "2 tiếng",
                            icon = Icons.Filled.Check
                        )

                        NutritionInfoItem(
                            label = "Cường độ tập luyện",
                            value = "Không có",
                            icon = null
                        )
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Tiêu đề
                        Text(
                            text = "Tâm trạng và cảm xúc",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Divider(thickness = 1.dp, color = Color.Black)


                        // Thông tin dinh dưỡng
                        NutritionInfoItem(
                            label = "Cảm xúc trong ngày",
                            value = "Cay vã ò",
                            icon = Icons.Filled.Check
                        )

                        NutritionInfoItem(
                            label = "Nguyên nhân ảnh hưởng",
                            value = "Chuỗi thua 5",
                            icon = Icons.Filled.Check
                        )

                        NutritionInfoItem(
                            label = "Ghi chú cá nhân",
                            value = "Không có",
                            icon = null
                        )
                    }
                }



                // Chat luong giac ngu

                // Spacer for bottom padding
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // Symptom entry dialog
        if (showDialog) {
            SymptomEntryDialog(
                onDismiss = { showDialog = false },
                onSubmit = { symptomData ->
                    lastSymptomData = symptomData
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun HeaderSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Daily Health Notes",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = textPrimaryColor,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .height(4.dp)
                .background(
                    brush = accentGradient,
                    shape = RoundedCornerShape(2.dp)
                )
        )
    }
}

@Composable
fun SymptomsSection(
    lastSymptomData: SymptomData?,
    onAddSymptom: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = primaryColor
            ),
        colors = CardDefaults.cardColors(
            containerColor = cardBackgroundColor
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Section header with add button
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Triệu chứng",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = textPrimaryColor
                )

                IconButton(
                    onClick = onAddSymptom,
                    modifier = Modifier
                        .size(44.dp)
                        .background(
                            brush = accentGradient,
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Rounded.AddCircle,
                        contentDescription = "Add symptom",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            // Divider
            Box(
                modifier = Modifier
                    .height(2.dp)
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFFF0F0F0),
                        shape = RoundedCornerShape(1.dp)
                    )
            )

            // Symptoms display
            lastSymptomData?.let { data ->
                SymptomCard(data)
            } ?: run {
                // Empty state
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Chưa có triệu chứng nào được ghi nhận",
                        color = textSecondaryColor,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun SymptomCard(data: SymptomData) {
    var expanded by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label = "rotation"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF9FAFF)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Header (always visible)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(
                        color = Color.Transparent
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Severity indicator
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(
                                color = when {
                                    data.severity < 2 -> Color(0xFF66BB6A)  // Green
                                    data.severity < 3 -> Color(0xFFFFEB3B)  // Yellow
                                    data.severity < 4 -> Color(0xFFFFA726)  // Orange
                                    else -> Color(0xFFEF5350)  // Red
                                },
                                shape = CircleShape
                            )
                    )

                    Text(
                        text = data.description.take(25) + if (data.description.length > 25) "..." else "",
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        color = textPrimaryColor
                    )
                }

                IconButton(
                    onClick = { expanded = !expanded },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ExpandMore,
                        contentDescription = if (expanded) "Show less" else "Show more",
                        tint = primaryColor,
                        modifier = Modifier.rotate(rotationState)
                    )
                }
            }

            // Expandable content
            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SymptomDetailRow(
                        label = "Mô tả triệu chứng:",
                        value = data.description
                    )

                    SymptomDetailRow(
                        label = "Thời gian xuất hiện:",
                        value = data.time
                    )

                    SymptomDetailRow(
                        label = "Mức độ:",
                        value = "${data.severity} - ${
                            when {
                                data.severity < 2 -> "Rất nhẹ"
                                data.severity < 3 -> "Nhẹ"
                                data.severity < 4 -> "Trung bình"
                                data.severity < 5 -> "Nặng"
                                else -> "Rất nặng"
                            }
                        }"
                    )

                    if (data.medications.isNotEmpty()) {
                        SymptomDetailRow(
                            label = "Thuốc đã dùng:",
                            value = data.medications.joinToString(", ")
                        )
                    }

                    // Action buttons
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = { /* Edit action */ },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = primaryColor
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Text("Chỉnh sửa")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ExpandableContainer(
    title: String,
    severityLevel: Int? = null,
    content: @Composable () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label = "rotation"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF9FAFF)
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Header (always visible)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(
                        color = Color.Transparent
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Severity indicator (only shown if severityLevel is provided)
                    if (severityLevel != null) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(
                                    color = when {
                                        severityLevel < 2 -> Color(0xFF66BB6A)  // Green
                                        severityLevel < 3 -> Color(0xFFFFEB3B)  // Yellow
                                        severityLevel < 4 -> Color(0xFFFFA726)  // Orange
                                        else -> Color(0xFFEF5350)  // Red
                                    },
                                    shape = CircleShape
                                )
                        )
                    }

                    Text(
                        text = title.take(25) + if (title.length > 25) "..." else "",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 22.sp,
                        color = Color(0xFF333333) // textPrimaryColor
                    )
                }

                IconButton(
                    onClick = { expanded = !expanded },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ExpandMore,
                        contentDescription = if (expanded) "Thu gọn" else "Mở rộng",
                        tint = Color(0xFF7F7FD5), // primaryColor
                        modifier = Modifier.rotate(rotationState)
                    )
                }
            }

            // Expandable content
            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    // Content passed from outside
                    content()
                }
            }
        }
    }
}

@Composable
fun SymptomDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.SemiBold,
            color = textPrimaryColor,
            modifier = Modifier.padding(end = 4.dp)
        )
        Text(
            text = value,
            color = textSecondaryColor
        )
    }
}

@Composable
fun Temp() {
    var expanded by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label = "rotation"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = secondaryColor
            ),
        colors = CardDefaults.cardColors(
            containerColor = cardBackgroundColor
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.Transparent),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Nhiệt độ cơ thể: 37.5°C",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = textPrimaryColor
                )

                IconButton(
                    onClick = { expanded = !expanded },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ExpandMore,
                        contentDescription = if (expanded) "Show less" else "Show more",
                        tint = secondaryColor,
                        modifier = Modifier.rotate(rotationState)
                    )
                }
            }

            // Expandable content
            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                BodyTemperatureChart(LocalDate.now().dayOfMonth)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBarDate(navController: NavHostController? = null) {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val today = LocalDate.now()

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .shadow(8.dp),
        shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            // Date navigation
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                // Close button
                IconButton(
                    onClick = {
                        if (navController != null) {
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    colors = IconButtonDefaults.iconButtonColors(secondaryColor)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.White
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth(0.7f)
                ) {
                    // Previous day button
                    IconButton(
                        onClick = { selectedDate = selectedDate.minusDays(1) },
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFF0F4FF))
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Previous day",
                            tint = primaryColor
                        )
                    }

                    // Current date
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFEEF2FF)
                        ),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = selectedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = textPrimaryColor
                            )
                        }
                    }

                    // Next day button
                    IconButton(
                        onClick = { selectedDate = selectedDate.plusDays(1) },
                        enabled = selectedDate.isBefore(today),
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(
                                if (selectedDate.isBefore(today)) Color(0xFFF0F4FF)
                                else Color(0xFFEEEEEE)
                            )
                    ) {
                        Icon(
                            Icons.Default.ArrowForward,
                            contentDescription = "Next day",
                            tint = if (selectedDate.isBefore(today)) primaryColor else Color.Gray
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun MealColumn(
    title: String,
    meals: List<String>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.primary
        )

        meals.forEach { meal ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                var isChecked by remember { mutableStateOf(false) }

                Checkbox(
                    checked = isChecked,
                    onCheckedChange = { isChecked = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colorScheme.primary,
                        uncheckedColor = MaterialTheme.colorScheme.outline
                    ),
                    modifier = Modifier.size(20.dp)
                )

                Text(
                    text = meal,
                    fontSize = 14.sp,
                    color = if (isChecked)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
fun VerticalDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = 1.dp,
    color: Color = MaterialTheme.colorScheme.outline
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .width(thickness)
            .background(color)
    )
}

@Composable
fun NutritionInfoItem(
    label: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector?
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                    append("$label: ")
                }
                append(value)
            },
            modifier = Modifier.weight(1f)
        )

        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}