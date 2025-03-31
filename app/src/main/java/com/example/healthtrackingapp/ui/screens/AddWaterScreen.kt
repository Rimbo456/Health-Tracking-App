package com.example.healthtrackingapp.ui.screens

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.example.healthtrackingapp.R
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import showNotification

@SuppressLint("MissingInflatedId")
@Composable
fun AddWaterScreen(
    navController: NavHostController
) {
    val db = FirebaseFirestore.getInstance()
    var user by remember { mutableStateOf(Firebase.auth.currentUser) }

    var weight by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        db.collection("users")
            .document(user!!.uid)
            .get()
            .addOnSuccessListener { document ->
                weight = document.getLong("weight")?.toInt() ?: 0
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreError", "Error getting weight data: ${e.message}")
            }
    }

    // xin quyền thông báo
    val context = LocalContext.current

    // Xử lý xin quyền nếu cần

    Scaffold { innerPadding ->
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            factory = { context ->
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.nhap_luong_nuoc_tieu_thu, null, false)

                val btnClose = view.findViewById<ImageView>(R.id.imgDong)
                btnClose.setOnClickListener {
                    navController.popBackStack()
                }

                val edtLuongNuoc = view.findViewById<EditText>(R.id.edtLuongNuoc)

                val btnSave = view.findViewById<Button>(R.id.btnXacNhanOTP)
                btnSave.setOnClickListener {

                    db.collection("users")
                        .document(user!!.uid)
                        .collection("water")
                        .add(
                            hashMapOf(
                                "luongnuoc" to edtLuongNuoc.text.toString(),
                                "timestamp" to Timestamp.now()
                            )
                        )
                    val luongnuoc =
                        edtLuongNuoc.text.toString().filter { it.isDigit() }.toInt() / 1000
                    val luongnuocNeed = 0.033 * weight
                    when (true) {
                        (luongnuoc < luongnuocNeed) -> {
                            val thieu = luongnuocNeed - luongnuoc
                            db.collection("users")
                                .document(user!!.uid)
                                .collection("notification")
                                .add(
                                    hashMapOf(
                                        "title" to "Bạn cần uống nước",
                                        "description" to "Bạn đã không uống đủ lượng nước ngày hôm nay, cơ thể bạn còn thiếu $thieu ml để hoạt động tốt",
                                        "icon" to "WaterDrop",
                                        "color" to "0xFF03A9F4",
                                        "timestamp" to Timestamp.now(),
                                        "unread" to true
                                    )
                                )
                            context.showNotification(
                                channelId = "water_chanel",
                                notificationId = 1,
                                title = "Bạn cần uống nước",
                                content = "Bạn đã không uống đủ lượng nước ngày hôm nay, cơ thể bạn còn thiếu $thieu ml để hoạt động tốt"
                            )
                        }

                        else -> {
                            db.collection("users")
                                .document(user!!.uid)
                                .collection("notification")
                                .add(
                                    hashMapOf(
                                        "title" to "Đã uống đủ nước",
                                        "description" to "Chúc mừng bạn đã uống đủ nước cho hôm nay",
                                        "icon" to "WaterDrop",
                                        "color" to "0xFF03A9F4",
                                        "timestamp" to Timestamp.now(),
                                        "unread" to true
                                    )
                                )
                            context.showNotification(
                                channelId = "water_chanel",
                                notificationId = 1,
                                title = "Đã uống đủ nước",
                                content = "Chúc mừng bạn đã uống đủ nước cho hôm nay"
                            )
                        }
                    }
                    navController.popBackStack()
                }


                view
            }
        )
    }
}