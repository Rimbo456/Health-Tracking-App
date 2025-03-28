package com.example.healthtrackingapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.healthtrackingapp.R
import com.example.healthtrackingapp.ui.components.TopBarForAdd
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

@Composable
fun BloodPressureScreen(
    navController: NavHostController,
) {
    val db = FirebaseFirestore.getInstance()
    var user by remember { mutableStateOf(Firebase.auth.currentUser) }

    var tamthu by remember { mutableStateOf("") }
    var tamtruong by remember { mutableStateOf("") }
    var nhiptim by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.padding(WindowInsets.systemBars.asPaddingValues()),
        topBar = { TopBarForAdd(navController) },
        bottomBar = {
            BottomBarForAdd(
                db = db,
                user = user!!,
                tamthu = tamthu,
                tamtruong = tamtruong,
                nhiptim = nhiptim,
                navController = navController
            )
        }
    ) { innerPadding ->
        Image(
            painter = painterResource(id = R.drawable.nen_app),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.05f))
            Image(
                painter = painterResource(id = R.drawable.reshot_icon_blood_pressure_zvjhstxe8u),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight(0.2f)
                    .fillMaxWidth()
            )
            Text(
                text = "Nhap so do huyet ap",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 20.dp),
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .weight(0.3f)
                            .shadow(elevation = 10.dp, shape = RoundedCornerShape(20.dp))
                            .clip(RoundedCornerShape(20.dp))
                            .border(
                                width = 1.dp,
                                color = Color.LightGray,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .background(Color.White)
                            .padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        BasicTextField(
                            value = tamthu,
                            onValueChange = {
                                if (it.length <= 2) {
                                    tamthu = it
                                }
                            },
                            singleLine = true,
                            textStyle = TextStyle(fontSize = 30.sp, textAlign = TextAlign.Start),
                            modifier = Modifier
                                .width(IntrinsicSize.Min)
                                .padding(vertical = 18.dp)
                        )
                        Text(
                            text = "Tam thu",
                            fontSize = 14.sp
                        )
                    }
                    Column(
                        modifier = Modifier
                            .weight(0.3f)
                            .shadow(elevation = 10.dp, shape = RoundedCornerShape(20.dp))
                            .clip(RoundedCornerShape(20.dp))
                            .border(
                                width = 1.dp,
                                color = Color.LightGray,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .background(Color.White)
                            .padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        BasicTextField(
                            value = tamtruong,
                            onValueChange = {
                                if (it.length <= 2) {
                                    tamtruong = it
                                }
                            },
                            singleLine = true,
                            textStyle = TextStyle(fontSize = 30.sp, textAlign = TextAlign.Start),
                            modifier = Modifier
                                .width(IntrinsicSize.Min)
                                .padding(vertical = 18.dp, horizontal = 20.dp)
                        )
                        Text(
                            text = "Tam truong",
                            fontSize = 14.sp
                        )
                    }
                    Column(
                        modifier = Modifier
                            .weight(0.3f)
                            .shadow(elevation = 10.dp, shape = RoundedCornerShape(20.dp))
                            .clip(RoundedCornerShape(20.dp))
                            .border(
                                width = 1.dp,
                                color = Color.LightGray,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .background(Color.White)
                            .padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        BasicTextField(
                            value = nhiptim,
                            onValueChange = {
                                if (it.length <= 3) {
                                    nhiptim = it
                                }
                            },

                            singleLine = true,
                            textStyle = TextStyle(fontSize = 30.sp, textAlign = TextAlign.Start),
                            modifier = Modifier
                                .width(IntrinsicSize.Min)
                                .padding(vertical = 18.dp, horizontal = 20.dp)
                        )
                        Text(
                            text = "Nhip tim",
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BottomBarForAdd(
    db: FirebaseFirestore,
    user: FirebaseUser,
    tamthu: String,
    tamtruong: String,
    nhiptim: String,
    navController: NavHostController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 40.dp, start = 30.dp, end = 30.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.Black)
            .clickable {
                db.collection("users")
                    .document(user.uid)
                    .collection("blood_pressure")
                    .add(
                        hashMapOf(
                            "tamthu" to tamthu,
                            "tamtruong" to tamtruong,
                            "nhiptim" to nhiptim,
                            "timestamp" to Timestamp.now()
                        )
                    )
                when (true) {
                    ((nhiptim.toInt() >= 60) and (nhiptim.toInt() <= 100)) -> {
                        db.collection("users")
                            .document(user.uid)
                            .collection("notification")
                            .add(
                                hashMapOf(
                                    "title" to "Nhịp tim của bạn",
                                    "description" to "Nhịp tim của bạn hiện tại là $nhiptim, tốt cho sức khỏe",
                                    "icon" to "Favorite",
                                    "color" to "0xFFBD2E4B",
                                    "timestamp" to Timestamp.now(),
                                    "unread" to true
                                )
                            )
                    }

                    (nhiptim.toInt() > 100) -> {
                        db.collection("users")
                            .document(user.uid)
                            .collection("notification")
                            .add(
                                hashMapOf(
                                    "title" to "Cảnh báo nhịp tim",
                                    "description" to "Nhịp tim của bạn hiện tại là $nhiptim, cao bất thường",
                                    "icon" to "Favorite",
                                    "color" to "0xFFBD2E4B",
                                    "timestamp" to Timestamp.now(),
                                    "unread" to true
                                )
                            )
                    }

                    else -> {
                        db.collection("users")
                            .document(user.uid)
                            .collection("notification")
                            .add(
                                hashMapOf(
                                    "title" to "Cảnh báo nhịp tim",
                                    "description" to "Nhịp tim của bạn hiện tại là $nhiptim, nhịp tim thấp bất thường",
                                    "icon" to "Favorite",
                                    "color" to "0xFFBD2E4B",
                                    "timestamp" to Timestamp.now(),
                                    "unread" to true
                                )
                            )
                    }
                }
                when (true) {
                    (((tamthu.toInt() >= 90) and (tamthu.toInt() <= 120)) and ((tamtruong.toInt() >= 60) and (tamtruong.toInt() <= 80))) -> {
                        db.collection("users")
                            .document(user.uid)
                            .collection("notification")
                            .add(
                                hashMapOf(
                                    "title" to "Huyết áp của bạn",
                                    "description" to "Huyết áp của bạn hiện tại là $tamthu/$tamtruong, không có gì bất thường",
                                    "icon" to "MonitorHeart",
                                    "color" to "0xFFBD2E4B",
                                    "timestamp" to Timestamp.now(),
                                    "unread" to true
                                )
                            )
                    }

                    (((tamthu.toInt() < 90)) and ((tamtruong.toInt() < 60))) -> {
                        db.collection("users")
                            .document(user.uid)
                            .collection("notification")
                            .add(
                                hashMapOf(
                                    "title" to "Cảnh báo huyết áp",
                                    "description" to "Huyết áp của bạn hiện tại là $tamthu/$tamtruong. Huyết áp thấp, cảnh báo có thể gây chóng mặt, ngất xỉu",
                                    "icon" to "MonitorHeart",
                                    "color" to "0xFFBD2E4B",
                                    "timestamp" to Timestamp.now(),
                                    "unread" to true
                                )
                            )
                    }
                    (((tamthu.toInt() >= 121) and (tamthu.toInt() <= 139)) and ((tamtruong.toInt() >= 81) and (tamtruong.toInt() <= 89))) -> {
                        db.collection("users")
                            .document(user.uid)
                            .collection("notification")
                            .add(
                                hashMapOf(
                                    "title" to "Cảnh báo huyết áp",
                                    "description" to "Huyết áp của bạn hiện tại là $tamthu/$tamtruong. Cảnh báo tiền cao huyết áp",
                                    "icon" to "MonitorHeart",
                                    "color" to "0xFFBD2E4B",
                                    "timestamp" to Timestamp.now(),
                                    "unread" to true
                                )
                            )
                    }
                    (((tamthu.toInt() >= 140) and (tamthu.toInt() <= 159)) and ((tamtruong.toInt() >= 90) and (tamtruong.toInt() <= 99))) -> {
                        db.collection("users")
                            .document(user.uid)
                            .collection("notification")
                            .add(
                                hashMapOf(
                                    "title" to "Cảnh báo huyết áp",
                                    "description" to "Huyết áp của bạn hiện tại là $tamthu/$tamtruong. Cảnh báo cao huyết áp cấp độ 1, cần được điều trị",
                                    "icon" to "MonitorHeart",
                                    "color" to "0xFFBD2E4B",
                                    "timestamp" to Timestamp.now(),
                                    "unread" to true
                                )
                            )
                    }

                    else -> {
                        db.collection("users")
                            .document(user.uid)
                            .collection("notification")
                            .add(
                                hashMapOf(
                                    "title" to "Cảnh báo huyết áp",
                                    "description" to "Huyết áp của bạn hiện tại là $tamthu/$tamtruong. Cảnh báo cao huyết áp cấp độ 2, có thể gây nguy hiểm tới tính mạng",
                                    "icon" to "MonitorHeart",
                                    "color" to "0xFFBD2E4B",
                                    "timestamp" to Timestamp.now(),
                                    "unread" to true
                                )
                            )
                    }
                }
                navController.popBackStack()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "Save",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(vertical = 10.dp)
        )
    }
}