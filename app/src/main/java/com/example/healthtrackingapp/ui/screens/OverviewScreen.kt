package com.example.healthtrackingapp.ui.screens

import android.net.Uri
import android.util.Log
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
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.LocalDining
import androidx.compose.material.icons.filled.Mood
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.SportsMartialArts
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.WaterDrop
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.healthtrackingapp.R
import com.example.healthtrackingapp.ui.components.BloodGlucoseChart
import com.example.healthtrackingapp.ui.components.BloodOxygenLevelChart
import com.example.healthtrackingapp.ui.components.BodyTemperatureChart
import com.example.healthtrackingapp.ui.components.ExpandCard
import com.example.healthtrackingapp.ui.components.HeartRateChart
import com.example.healthtrackingapp.ui.components.JetpackComposeBasicLineChart
import com.example.healthtrackingapp.ui.components.SymptomData
import com.example.healthtrackingapp.ui.components.SymptomEntryDialog
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

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
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var showEditSymptom by remember { mutableStateOf(false) }
    var lastSymptomData by remember { mutableStateOf<SymptomData?>(null) }
    var symptomDatas by remember { mutableStateOf(listOf<SymptomData>()) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var usedFood by remember { mutableStateOf("") }
    var usedFoodAno by remember { mutableStateOf("") }
    var buoian by remember { mutableStateOf(listOf<String>()) }
    var weight by remember { mutableStateOf(0) }
    var height by remember { mutableStateOf(0) }
    var age by remember { mutableStateOf(0) }
    var gender by remember { mutableStateOf(true) }
    var workoutType by remember { mutableStateOf("") }
    var durationWorkout by remember { mutableStateOf("") }
    var intensityValue by remember { mutableStateOf("") }
    var workoutSessions by remember { mutableStateOf(listOf<WorkoutSession>()) }
    var mood by remember { mutableStateOf("") }
    var sleep by remember { mutableStateOf("") }
    var sleepEfficiency by remember { mutableStateOf("") }
    var feedbackSleep by remember { mutableStateOf("") }
    var waterInput by remember { mutableStateOf("") }
    var heartRate by remember { mutableStateOf("") }
    var tamthu by remember { mutableStateOf("") }
    var tamtruong by remember { mutableStateOf("") }
    var refreshSymptom by remember { mutableStateOf(0) }


    val db = FirebaseFirestore.getInstance()
    var user by remember { mutableStateOf(Firebase.auth.currentUser) }

    LaunchedEffect(selectedDate) {
        workoutSessions = mutableListOf()
        symptomDatas = mutableListOf()
        lastSymptomData = null
        refreshSymptom++
        // Xác định timestamp đầu và cuối ngày
        val calendar = Calendar.getInstance()
        calendar.set(
            selectedDate.year,
            selectedDate.monthValue - 1,
            selectedDate.dayOfMonth,
            0,
            0,
            0
        )
        val startOfDay = calendar.timeInMillis

        calendar.set(
            selectedDate.year,
            selectedDate.monthValue - 1,
            selectedDate.dayOfMonth,
            23,
            59,
            59
        )
        val endOfDay = calendar.timeInMillis

        // Sử dụng mutableListOf để tích lũy dữ liệu
        val foodList = mutableListOf<String>()
        val foodAnoList = mutableListOf<String>()
        val mealTypeList = mutableListOf<String>()
        val workoutList = mutableListOf<Map<String, String>>()
        val moodList = mutableListOf<String>()

        db.collection("users")
            .document(user!!.uid)
            .collection("food")
            .whereGreaterThanOrEqualTo("timestamp", startOfDay)
            .whereLessThanOrEqualTo("timestamp", endOfDay)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    usedFood = "Chưa có dữ liệu"
                    usedFoodAno = "Chưa có dữ liệu"
                    buoian = listOf("Chưa có dữ liệu")
                } else {
                    for (document in documents) {
                        document.getString("foodInput")?.takeIf { it.isNotEmpty() }
                            ?.let { foodList.add(it) }

                        val supplements = listOfNotNull(
                            document.getString("vitaminInput"),
                            document.getString("milkInput"),
                            document.getString("supplementInput")
                        ).filter { it.isNotEmpty() }

                        if (supplements.isNotEmpty()) {
                            foodAnoList.addAll(supplements)
                        }

                        document.getString("mealType")?.takeIf { it.isNotEmpty() }
                            ?.let { mealTypeList.add(it) }
                    }
                }

                // Cập nhật biến trạng thái sau khi có kết quả
                usedFood = foodList.joinToString(", ")
                usedFoodAno = foodAnoList.joinToString(", ")
                buoian = mealTypeList
            }
            .addOnFailureListener { e ->
                // Xử lý lỗi
                Log.e("FirestoreError", "Error getting food documents: ${e.message}")
            }

        db.collection("users")
            .document(user!!.uid)
            .collection("mood")
            .whereGreaterThanOrEqualTo("timestamp", startOfDay)
            .whereLessThanOrEqualTo("timestamp", endOfDay)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    mood = "Chưa có dữ liệu"
                } else {
                    for (document in documents) {
                        mood = document.getString("moodLevel") ?: ""
                    }
                }
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreError", "Error getting mood documents: ${e.message}")
            }

        db.collection("users")
            .document(user!!.uid)
            .collection("water")
            .whereGreaterThanOrEqualTo("timestamp", startOfDay)
            .whereLessThanOrEqualTo("timestamp", endOfDay)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    waterInput = "Chưa có dữ liệu"
                } else {
                    for (document in documents) {
                        waterInput = document.getString("luongnuoc") ?: ""
                    }
                }
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreError", "Error getting water documents: ${e.message}")
            }

        db.collection("users")
            .document(user!!.uid)
            .collection("workout")
            .whereGreaterThanOrEqualTo("timestamp", startOfDay)
            .whereLessThanOrEqualTo("timestamp", endOfDay)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    workoutType = "Chưa có dữ liệu"
                    durationWorkout = "Chưa có dữ liệu"
                    intensityValue = "Chưa có dữ liệu"
                } else {
                    for (document in documents) {
                        val workout = mapOf(
                            "type" to (document.getString("workoutType") ?: ""),
                            "duration" to (document.getString("duration") ?: ""),
                            "intensity" to (document.getString("intensityValue") ?: "")
                        )
                        workoutList.add(workout)
                    }
                }

                // Nếu có dữ liệu tập luyện, sử dụng mục đầu tiên (hoặc xử lý nhiều mục nếu cần)
                if (workoutList.isNotEmpty()) {
                    workoutSessions = workoutList.map {
                        WorkoutSession(
                            workoutType = it["type"] ?: "",
                            duration = it["duration"] ?: "",
                            intensity = it["intensity"] ?: ""
                        )
                    }
                    workoutType = workoutList[0]["type"] ?: ""
                    durationWorkout = workoutList[0]["duration"] ?: ""
                    intensityValue = workoutList[0]["intensity"] ?: ""
                }
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreError", "Error getting workout documents: ${e.message}")
            }

        db.collection("users")
            .document(user!!.uid)
            .get()
            .addOnSuccessListener { document ->
                weight = document.getLong("weight")?.toInt() ?: 0
                height = document.getLong("height")?.toInt() ?: 0
                age = document.getLong("age")?.toInt() ?: 0
                gender = document.getBoolean("gender") ?: true
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreError", "Error getting user data: ${e.message}")
            }

        db.collection("users")
            .document(user!!.uid)
            .collection("sleep")
            .whereGreaterThanOrEqualTo("timestamp", startOfDay)
            .whereLessThanOrEqualTo("timestamp", endOfDay)
            .limit(1)
            .get()
            .addOnSuccessListener { result ->
                if (result.isEmpty) {
                    sleep = "Chưa có dữ liệu"
                } else {
                    for (document in result) {
                        sleep = document.getString("giacngu") ?: ""
                        val sleepInt = sleep.takeIf { it.isNotEmpty() }?.toIntOrNull() ?: 0
                        val efficencyIndex = ((sleepInt - 1.5) / sleepInt) * 100
                        sleepEfficiency = when (true) {
                            (efficencyIndex >= 85) -> "Giấc ngủ tốt"
                            (efficencyIndex >= 75) and (efficencyIndex <= 85) -> "Giấc ngủ khá"
                            else -> "Giấc ngủ kém"
                        }
                        feedbackSleep = document.getString("quality") ?: ""
                    }
                }
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreError", "Error getting sleep", e)
                // Xử lý lỗi ở đây
            }

        /*db.collection("users")
            .document(user!!.uid)
            .collection("blood_pressure")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .addOnSuccessListener { result ->
                if (result.isEmpty) {
                    tamthu = "Chưa có dữ liệu"
                    tamtruong = "Chưa có dữ liệu"
                    heartRate = "Chưa có dữ liệu"
                } else {
                    for (document in result) {
                        tamthu = document.getString("tamthu") ?: ""
                        tamtruong = document.getString("tamtruong") ?: ""
                        heartRate = document.getString("nhiptim") ?: ""
                    }
                }
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreError", "Error getting blood_presure", e)
                // Xử lý lỗi ở đây
            }*/

    }

    LaunchedEffect(refreshSymptom) {
        // Xác định timestamp đầu và cuối ngày
        val calendar = Calendar.getInstance()
        calendar.set(
            selectedDate.year,
            selectedDate.monthValue - 1,
            selectedDate.dayOfMonth,
            0,
            0,
            0
        )
        val startOfDay = calendar.timeInMillis

        calendar.set(
            selectedDate.year,
            selectedDate.monthValue - 1,
            selectedDate.dayOfMonth,
            23,
            59,
            59
        )
        val endOfDay = calendar.timeInMillis

        val symptomList = mutableListOf<SymptomData>()

        db.collection("users")
            .document(user!!.uid)
            .collection("symptom")
            .whereGreaterThanOrEqualTo("timestamp", startOfDay)
            .whereLessThanOrEqualTo("timestamp", endOfDay)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    symptomList.clear()
                } else {
                    for (document in documents) {
                        val symptomData =
                            document.toObject(SymptomData::class.java)?.copy(id = document.id)
                        if (symptomData != null) {
                            symptomList.add(symptomData)
                        }
                    }
                }
                symptomDatas = symptomList
                lastSymptomData = null
            }

    }

    Scaffold(
        modifier = Modifier.padding(WindowInsets.systemBars.asPaddingValues()),
        topBar = {
            TopBarDate(
                navController,
                selectedDate = selectedDate,
                onDateChange = { selectedDate = it })
        },
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
                    onAddSymptom = { showDialog = true },
                    symptomDatas = symptomDatas,
                    onRefresh = {
                        refreshSymptom++
                    }
                )

                ExpandableContainer(
                    title = "Biểu đồ chỉ số cơ thể",
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        NutritionInfoItem(
                            label = "Chỉ số Khối cơ thể (BMI)",
                            value = try {
                                if (height > 0) {
                                    val heightInMeter = height / 100.0
                                    val bmi = weight / (heightInMeter * heightInMeter)
                                    val bmiFormatted = String.format("%.1f", bmi)

                                    // Thêm đánh giá BMI
                                    val bmiCategory = when {
                                        bmi < 18.5 -> "Thiếu cân"
                                        bmi < 25 -> "Bình thường"
                                        bmi < 30 -> "Thừa cân"
                                        else -> "Béo phì"
                                    }

                                    "$bmiFormatted - $bmiCategory"
                                } else {
                                    "Chưa có dữ liệu"
                                }
                            } catch (e: Exception) {
                                "Chưa có dữ liệu"
                            },
                            icon = null
                        )
                        NutritionInfoItem(
                            label = "Tỷ lệ mỡ cơ thể (BFP)",
                            value = try {
                                if (gender == true) {
                                    val heightInMeter = height / 100.0
                                    val bmi = weight / (heightInMeter * heightInMeter)

                                    val bfp = 1.2 * bmi + 0.23 * age - 16.2
                                    val bfpFortmatted = String.format("%.1f", bfp)
                                    val bfpCategory = when {
                                        ((bmi < 20) and (bmi > 10)) -> "Bình thường"
                                        else -> "Béo phì"
                                    }
                                    "$bfpFortmatted - $bfpCategory"
                                } else {
                                    val heightInMeter = height / 100.0
                                    val bmi = weight / (heightInMeter * heightInMeter)

                                    val bfp = 1.2 * bmi + 0.23 * age - 16.2
                                    val bfpFortmatted = String.format("%.1f", bfp)
                                    val bfpCategory = when {
                                        ((bmi < 20) and (bmi > 10)) -> "Bình thường"
                                        else -> "Béo phì"
                                    }
                                    "$bfpFortmatted - $bfpCategory"
                                }
                            } catch (e: Exception) {
                                "Chưa có dữ liệu"
                            },
                            icon = null
                        )
                        NutritionInfoItem(
                            label = "Tỷ lệ trao đổi chất cơ bản (BMR)",
                            value = try {
                                if (gender == true) {

                                    val bmr = 88.36 + (13.4 * weight) + (4.8 * height) - (5.7 * age)
                                    val bmrFortmatted = String.format("%.1f", bmr)

                                    "$bmrFortmatted"
                                } else {
                                    val bmr = 447.6 + (9.2 * weight) + (3.1 * height) - (4.3 * age)
                                    val bmrFortmatted = String.format("%.1f", bmr)

                                    "$bmrFortmatted"
                                }
                            } catch (e: Exception) {
                                "Chưa có dữ liệu"
                            },
                            icon = null
                        )
                        NutritionInfoItem(
                            label = "Lượng nước cần uống mỗi ngày",
                            value = try {
                                val wa = 0.033 * weight
                                val waFormatted = String.format("%.1f", wa)
                                "$waFormatted lít"
                            } catch (e: Exception) {
                                "Chưa có dữ liệu"
                            },
                            icon = null
                        )
                        // Card for Temperature
                        ExpandCard(
                            title = "Nhiệt độ cơ thể",
                            value = null,
                            unit = "°C",
                            colorEle = Color(red = 218, green = 115, blue = 35, alpha = 255),
                            content = {
                                BodyTemperatureChart(selectedDate = selectedDate)
                            }
                        )

                        // Card for Blood Presure
                        ExpandCard(
                            title = "Huyết áp",
                            value = null,
                            unit = "mmHg",
                            colorEle = Color(red = 255, green = 32, blue = 32, alpha = 255),
                            content = {
                                JetpackComposeBasicLineChart(selectedDate = selectedDate)
                            }
                        )

                        // Heart Rate
                        ExpandCard(
                            title = "Nhịp tim",
                            value = null,
                            unit = "BPM",
                            colorEle = Color(red = 255, green = 32, blue = 32, alpha = 255),
                            content = {
                                HeartRateChart(selectedDate = selectedDate)
                            }
                        )

                        // blood glucose
                        ExpandCard(
                            title = "Đường huyết",
                            value = null,
                            unit = "mg/dL",
                            colorEle = Color(red = 177, green = 255, blue = 32, alpha = 255),
                            content = {
                                BloodGlucoseChart(selectedDate = selectedDate)
                            }
                        )

                        //blood oxygen levels
                        ExpandCard(
                            title = "Nồng độ oxygen trong máu",
                            value = null,
                            unit = "%",
                            colorEle = Color(red = 32, green = 229, blue = 255, alpha = 255),
                            content = {
                                BloodOxygenLevelChart(selectedDate = selectedDate)
                            }
                        )
                    }
                }

                //Card bữa ăn
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
                        // Tiêu đề với biểu tượng
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Restaurant,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Bữa ăn trong ngày",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.weight(1f))
                        }

                        Divider(thickness = 1.dp, color = Color.LightGray)

                        // Khu vực bữa ăn
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            MealColumn(
                                title = "Sáng",
                                meals = listOf("Chính", "Phụ"),
                                modifier = Modifier.weight(1f),
                                buoian = buoian
                            )

                            VerticalDivider(
                                thickness = 1.dp,
                                color = Color.Black
                            )

                            MealColumn(
                                title = "Trưa",
                                meals = listOf("Chính", "Phụ"),
                                modifier = Modifier.weight(1f),
                                buoian = buoian
                            )

                            VerticalDivider(
                                thickness = 1.dp,
                                color = MaterialTheme.colorScheme.surfaceVariant
                            )

                            MealColumn(
                                title = "Chiều",
                                meals = listOf("Chính", "Phụ"),
                                modifier = Modifier.weight(1f),
                                buoian = buoian
                            )
                        }

                        Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.surfaceVariant)

                        // Thông tin dinh dưỡng
                        NutritionInfoItem(
                            label = "Lượng nước đã uống",
                            value = waterInput,
                            icon = painterResource(R.drawable.ic_water_100)
                        )

                        NutritionInfoItem(
                            label = "Thực phẩm đã tiêu thụ",
                            value = usedFood,
                            icon = painterResource(R.drawable.ic_hot_dog_100)
                        )

                        NutritionInfoItem(
                            label = "Vitamin/sữa/thuốc bổ",
                            value = usedFoodAno,
                            icon = painterResource(R.drawable.ic_medicine)
                        )
                    }
                }

                WorkoutSessionsCard(workoutSessions)

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
                        // Tiêu đề với biểu tượng
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Mood,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Tâm trạng và cảm xúc",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.weight(1f))
                        }

                        Divider(thickness = 1.dp, color = Color.LightGray)

                        NutritionInfoItem(
                            label = "Cảm xúc trong ngày",
                            value = mood,
                            icon = painterResource(R.drawable.ic_wedding_day_100)
                        )

                        /*NutritionInfoItem(
                            label = "Nguyên nhân ảnh hưởng",
                            value = "Chuỗi thua 5",
                            icon = null
                        )*/

                        NutritionInfoItem(
                            label = "Ghi chú cá nhân",
                            value = "Không có",
                            icon = painterResource(R.drawable.ic_notee_100)
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
                        // Tiêu đề với biểu tượng
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Bedtime,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Chất lượng giấc ngủ",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.weight(1f))
                        }

                        Divider(thickness = 1.dp, color = Color.LightGray)

                        NutritionInfoItem(
                            label = "Tổng thời gian ngủ",
                            value = sleep,
                            icon = painterResource(R.drawable.ic_time_240)
                        )

                        NutritionInfoItem(
                            label = "Hiệu suất giấc ngủ",
                            value = sleepEfficiency,
                            icon = painterResource(R.drawable.ic_sleep_100)
                        )

                        NutritionInfoItem(
                            label = "Đánh giá giấc ngủ",
                            value = feedbackSleep,
                            icon = painterResource(R.drawable.ic_assessment_100)
                        )
                    }
                }


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
        if (showEditSymptom) {
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
    onAddSymptom: () -> Unit,
    symptomDatas: List<SymptomData>,
    onRefresh: () -> Unit
) {
    val db = FirebaseFirestore.getInstance()
    var user by remember { mutableStateOf(Firebase.auth.currentUser) }

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
                SymptomCard(
                    data,
                    onDeleted = {
                        db.collection("users")
                            .document(user!!.uid)
                            .collection("symptom")
                            .document(data.id)
                            .delete()
                            .addOnSuccessListener {
                                onRefresh()
                            }
                    },
                    onEdit = {

                    }
                )
                onRefresh()
            } /*?: run {
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
            }*/

            if (symptomDatas.isNullOrEmpty()) {
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
            } else {
                symptomDatas.forEach { i ->
                    SymptomCard(
                        i,
                        onDeleted = {
                            db.collection("users")
                                .document(user!!.uid)
                                .collection("symptom")
                                .document(i.id)
                                .delete()
                                .addOnSuccessListener {
                                    onRefresh()
                                }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SymptomCard(
    data: SymptomData,
    onDeleted: () -> Unit = {},
    onEdit: () -> Unit = {}
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
                            onClick = { onEdit() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = primaryColor
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Text("Chỉnh sửa")
                        }
                        Button(
                            onClick = { onDeleted() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFF03E3E)
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Text("Xóa")
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBarDate(
    navController: NavHostController? = null,
    selectedDate: LocalDate,
    onDateChange: (LocalDate) -> Unit
) {
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
                        onClick = { onDateChange(selectedDate.minusDays(1)) },
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
                        onClick = { onDateChange(selectedDate.plusDays(1)) },
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
    modifier: Modifier = Modifier,
    buoian: List<String>?
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
            // Tạo tên đầy đủ của bữa ăn dựa trên title và meal
            val fullMealName = when {
                title.contains("sáng", ignoreCase = true) && meal.contains(
                    "Chính",
                    ignoreCase = true
                ) -> "Sáng"

                title.contains("sáng", ignoreCase = true) && meal.contains(
                    "Phụ",
                    ignoreCase = true
                ) -> "Phụ sáng"

                title.contains("trưa", ignoreCase = true) && meal.contains(
                    "Chính",
                    ignoreCase = true
                ) -> "Trưa"

                title.contains("trưa", ignoreCase = true) && meal.contains(
                    "Phụ",
                    ignoreCase = true
                ) -> "Phụ trưa"

                title.contains("chiều", ignoreCase = true) && meal.contains(
                    "Chính",
                    ignoreCase = true
                ) -> "Chiều"

                title.contains("chiều", ignoreCase = true) && meal.contains(
                    "Phụ",
                    ignoreCase = true
                ) -> "Phụ chiều"

                else -> meal // Fallback nếu không khớp với các trường hợp trên
            }

            // Kiểm tra xem bữa ăn này có trong danh sách buoian không
            val isChecked = remember(buoian) {
                buoian?.contains(fullMealName) ?: false
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = {
                        // Ở đây bạn nên thêm callback để cập nhật danh sách buoian
                        // Ví dụ: onMealCheckedChange(fullMealName, it)
                    },
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

/*@Composable
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
}*/

@Composable
fun WorkoutSessionsCard(
    workoutSessions: List<WorkoutSession>
) {
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
            // Tiêu đề với biểu tượng
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Filled.FitnessCenter,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Vận động và tập luyện",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "${workoutSessions.size} buổi tập",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Medium
                )
            }

            Divider(thickness = 1.dp, color = Color.LightGray)

            // Danh sách các buổi tập
            workoutSessions.forEachIndexed { index, session ->
                WorkoutSessionItem(session = session)

                // Thêm đường phân cách giữa các buổi tập, không thêm ở cuối
                if (index < workoutSessions.size - 1) {
                    Divider(
                        thickness = 1.dp,
                        color = Color.LightGray.copy(alpha = 0.5f),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun WorkoutSessionItem(session: WorkoutSession) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFFF5F9FF),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(12.dp)
    ) {
        // Tiêu đề buổi tập với ngày
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = session.workoutType,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        NutritionInfoItem(
            label = "Thời gian tập luyện",
            value = session.duration,
            icon = painterResource(R.drawable.ic_hourglass_100)
        )

        NutritionInfoItem(
            label = "Cường độ tập luyện",
            value = session.intensity,
            icon = painterResource(R.drawable.ic_strength_100)
        )

        /*// Hiển thị thông tin bổ sung nếu có
        if (session.additionalInfo.isNotEmpty()) {
            Spacer(modifier = Modifier.height(4.dp))
            session.additionalInfo.forEach { (key, value) ->
                NutritionInfoItem(
                    label = key,
                    value = value,
                    icon = null
                )
            }
        }*/
    }
}

@Composable
fun NutritionInfoItem(
    label: String,
    value: String,
    icon: Painter?
) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        if (icon != null) {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
        }

        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                    append("$label: ")
                }
                withStyle(style = SpanStyle(color = Color.DarkGray)) {
                    append(value)
                }
            },
            modifier = Modifier.weight(1f)
        )
    }
}

// Data class để lưu trữ thông tin buổi tập
data class WorkoutSession(
    val workoutType: String,
    val duration: String,
    val intensity: String,
)

// Dữ liệu mẫu
val sampleWorkoutSessions = listOf(
    WorkoutSession(
        workoutType = "Cardio",
        duration = "45 phút",
        intensity = "Trung bình",
    ),
    WorkoutSession(
        workoutType = "Tạ tự do",
        duration = "60 phút",
        intensity = "Cao",
    ),
    WorkoutSession(
        workoutType = "Yoga",
        duration = "30 phút",
        intensity = "Thấp",
    )
)