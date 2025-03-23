package com.example.healthtrackingapp.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.healthtrackingapp.R
import com.example.healthtrackingapp.ui.components.CardDashboard
import com.example.healthtrackingapp.ui.components.CircularCheckboxWithIcon
import com.example.healthtrackingapp.ui.components.ItemGoal
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun DashboardScreen(
    navController: NavHostController,
    modifier: Modifier? = null,
) {
    var textToShow by remember { mutableStateOf("") }
    val fullText = "Hôm nay bạn cảm thấy thế nào?"
    val coroutineScope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }
    var titleGoal by remember { mutableStateOf("") }
    var contentGoal by remember { mutableStateOf("") }
    var dateGoal by remember { mutableStateOf("") }
    var heartRate by remember { mutableStateOf("") }
    var tamthu by remember { mutableStateOf("") }
    var tamtruong by remember { mutableStateOf("") }
    var sleep by remember { mutableStateOf("") }
    var workoutDuration by remember { mutableStateOf("") }

    val db = FirebaseFirestore.getInstance()
    var user by remember { mutableStateOf(Firebase.auth.currentUser) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            fullText.forEachIndexed { index, _ ->
                textToShow = fullText.substring(0, index + 1)
                delay(50)
            }
        }
    }

    LaunchedEffect(Unit) {
        db.collection("users")
            .document(user!!.uid)
            .collection("goals")
            .limit(1)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    titleGoal = document.getString("title") ?: ""
                    contentGoal = document.getString("content") ?: ""
                    dateGoal = document.getString("date") ?: ""
                }
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreError", "Error getting goals", e)
                // Xử lý lỗi ở đây
            }

        db.collection("users")
            .document(user!!.uid)
            .collection("blood_pressure")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    tamthu = document.getString("tamthu") ?: ""
                    tamtruong = document.getString("tamtruong") ?: ""
                    heartRate = document.getString("nhiptim") ?: ""
                }
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreError", "Error getting blood_presure", e)
                // Xử lý lỗi ở đây
            }

        db.collection("users")
            .document(user!!.uid)
            .collection("sleep")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    sleep = document.getString("giacngu") ?: ""
                }
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreError", "Error getting sleep", e)
                // Xử lý lỗi ở đây
            }

        db.collection("users")
            .document(user!!.uid)
            .collection("workout")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    workoutDuration = document.getString("duration") ?: ""
                }
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreError", "Error getting workout", e)
                // Xử lý lỗi ở đây
            }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.nen_app),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight(0.15f)
                .fillMaxWidth(),
        ) {
            val calendar = Calendar.getInstance()
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val currentDate = dateFormat.format(calendar.time)
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Welcome",
                    fontFamily = FontFamily.Cursive,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = currentDate,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(56.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp)
                .clickable { showDialog = true },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Thêm",
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = textToShow,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.goal),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                IconButton(onClick = { navController.navigate("goalscreen") }) {
                    Icon(
                        imageVector = Icons.Filled.List,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Top
            ) {
                Card(
                    onClick = { navController.navigate("goalscreen") },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(15.dp)
                    ) {
                        Text(
                            text = "Tên mục tiêu: " + titleGoal,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )
                        Text(
                            text = "Nội dung: " + contentGoal,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )
                        Text(
                            text = "Ngày hoàn thành: " + dateGoal,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )
                    }
                }
//                Text(text = stringResource(id = R.string.notarget))
            }
        }
        Text(
            text = stringResource(id = R.string.overview),
            fontSize = 26.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth(0.9f)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.6f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CardDashboard(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    title = stringResource(id = R.string.heartrate),
                    value = heartRate.takeIf { it.isNotEmpty() }?.toIntOrNull() ?: 0,
                    unit = "bpm",
                    icon = Icons.Filled.Favorite,
                    color = CardDefaults.cardColors(
                        Color(red = 255, green = 226, blue = 226, alpha = 255)
                    ),
                    textColor = Color(red = 189, green = 46, blue = 75, alpha = 255)
                )
                Spacer(modifier = Modifier.width(10.dp))
                CardDashboard(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    title = stringResource(id = R.string.exercise),
                    value = workoutDuration.filter { it.isDigit() }.takeIf { it.isNotEmpty() }?.toIntOrNull() ?: 0,
                    unit = "min",
                    icon = Icons.Filled.ElectricBolt,
                    color = CardDefaults.cardColors(
                        Color(red = 254, green = 224, blue = 255, alpha = 255)
                    ),
                    textColor = Color(red = 111, green = 40, blue = 182, alpha = 255)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CardDashboard(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    title = stringResource(id = R.string.walking),
                    value = 10,
                    unit = "km",
                    icon = Icons.Filled.Flag,
                    color = CardDefaults.cardColors(
                        Color(red = 203, green = 248, blue = 248, alpha = 255)
                    ),
                    textColor = Color(red = 59, green = 155, blue = 131, alpha = 255)
                )
                Spacer(modifier = Modifier.width(10.dp))
                CardDashboard(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    title = stringResource(id = R.string.sleep),
                    value = sleep.takeIf { it.isNotEmpty() }?.toIntOrNull() ?: 0,
                    unit = "hrs",
                    icon = Icons.Filled.Hotel,
                    color = CardDefaults.cardColors(
                        Color(red = 207, green = 223, blue = 255, alpha = 255)
                    ),
                    textColor = Color(red = 41, green = 96, blue = 155, alpha = 255)
                )
            }
        }

    }
    if (showDialog) {
        FeelingDialog(onDismiss = { showDialog = false })
    }
}

@Composable
fun FeelingDialog(onDismiss: () -> Unit) {
    val db = FirebaseFirestore.getInstance()
    var user by remember { mutableStateOf(Firebase.auth.currentUser) }

    var indexx by remember { mutableStateOf(-1) }


    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.White, shape = RoundedCornerShape(16.dp))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Mức độ căng thẳng (Stress) của bạn hiện tại là?",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    listOf(
                        "Thư giãn",
                        "Hơi áp lực",
                        "Căng thẳng trung bình",
                        "Căng thẳng cao",
                        "Rất căng thẳng"
                    ).forEachIndexed { index, emoji ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    indexx = index
                                },
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = emoji,
                                fontSize = 15.sp,
                                modifier = Modifier
                            )
                            CircularCheckboxWithIcon(
                                isChecked = indexx == index,
                                onCheckedChange = { indexx = index }
                            )
                        }
                    }
                    if (indexx > -1) {
                        Text(
                            text = when (indexx) {
                                0 -> "Mọi thứ đều ổn, tôi cảm thấy thoải mái và kiểm soát tốt cuộc sống."
                                1 -> "Có một chút căng thẳng, nhưng tôi vẫn có thể kiểm soát được và tập trung vào công việc."
                                2 -> "Tôi đang cảm thấy áp lực, nhưng vẫn có thể đối mặt và tìm cách giải quyết."
                                3 -> "Mọi thứ bắt đầu trở nên quá tải, tôi cảm thấy kiệt sức và khó tập trung."
                                4 -> "Tôi bị choáng ngợp, không thể suy nghĩ rõ ràng và cần một khoảng thời gian để lấy lại bình tĩnh."
                                else -> ""
                            },
                            fontSize = 13.sp,
                            fontStyle = FontStyle.Italic,
                            modifier = Modifier.padding(bottom = 2.dp)
                        )
                    }
                }
                /*Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Nguyên nhân gây ra căng thẳng?",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )*/
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                        db.collection("users")
                            .document(user!!.uid)
                            .collection("mood")
                            .add(
                                hashMapOf(
                                    "moodLevel" to when (indexx) {
                                        0 -> "Thư giãn"
                                        1 -> "Hơi áp lực"
                                        2 -> "Căng thẳng trung bình"
                                        3 -> "Căng thẳng cao"
                                        4 -> "Rất căng thẳng"
                                        else -> ""
                                    },
                                    "timestamp" to System.currentTimeMillis(),
                                )
                            )
                        onDismiss()
                    }
                ) {
                    Text("Lưu")
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun DashboardScreenPreview() {
}