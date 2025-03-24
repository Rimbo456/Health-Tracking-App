package com.example.healthtrackingapp.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.example.healthtrackingapp.R
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

@SuppressLint("MissingInflatedId")
@Composable
fun AddTemperatureScreen(
    navController: NavHostController
) {
    val db = FirebaseFirestore.getInstance()
    var user by remember { mutableStateOf(Firebase.auth.currentUser) }

    Scaffold { innerPadding ->
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            factory = { context ->
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.nhap_nhiet_do, null, false)

                val btnClose = view.findViewById<ImageView>(R.id.imgDong)
                btnClose.setOnClickListener {
                    navController.popBackStack()
                }

                val edtLuongNuoc = view.findViewById<EditText>(R.id.edtLuongNuoc)

                val btnSave = view.findViewById<Button>(R.id.btnXacNhanOTP)
                btnSave.setOnClickListener {
                    db.collection("users")
                        .document(user!!.uid)
                        .collection("temperature")
                        .add(
                            hashMapOf(
                                "nhietdo" to edtLuongNuoc.text.toString(),
                                "timestamp" to Timestamp.now()
                            )
                        )
                    val temperature = edtLuongNuoc.text.toString().toDouble()
                    when (true) {
                        ((temperature >= 36.1)and(temperature <= 37.5)) -> {
                            db.collection("users")
                                .document(user!!.uid)
                                .collection("notification")
                                .add(
                                    hashMapOf(
                                        "title" to "Nhiệt độ cơ thể",
                                        "description" to "Nhiệt độ cơ thể bạn là "+temperature+" độ C, không có gì bất thường.",
                                        "icon" to "Thermostat",
                                        "color" to "0xFFBD2E4B",
                                        "timestamp" to Timestamp.now(),
                                        "unread" to true
                                    )
                                )
                        }
                        (temperature < 36.1) -> {
                            db.collection("users")
                                .document(user!!.uid)
                                .collection("notification")
                                .add(
                                    hashMapOf(
                                        "title" to "Nhiệt độ cơ thể",
                                        "description" to "Nhiệt độ cơ thể bạn là "+temperature+" độ C, cảnh báo nhiệt độ thấp",
                                        "icon" to "Thermostat",
                                        "color" to "0xFFBD2E4B",
                                        "timestamp" to Timestamp.now(),
                                        "unread" to true
                                    )
                                )
                        }
                        else -> {
                            db.collection("users")
                                .document(user!!.uid)
                                .collection("notification")
                                .add(
                                    hashMapOf(
                                        "title" to "Nhiệt độ cơ thể",
                                        "description" to "Nhiệt độ cơ thể bạn là "+temperature+" độ C, bạn đang bị sốt",
                                        "icon" to "Thermostat",
                                        "color" to "0xFFBD2E4B",
                                        "timestamp" to Timestamp.now(),
                                        "unread" to true
                                    )
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