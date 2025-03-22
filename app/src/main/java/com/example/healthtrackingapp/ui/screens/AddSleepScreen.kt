package com.example.healthtrackingapp.ui.screens

import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.example.healthtrackingapp.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

@Composable
fun AddSleepScreen(
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
            val view = LayoutInflater.from(context).inflate(R.layout.nhap_giac_ngu, null, false)

            val btnClose = view.findViewById<ImageView>(R.id.imgDong)
            btnClose.setOnClickListener {
                navController.popBackStack()
            }

            val edtGiacngu = view.findViewById<EditText>(R.id.edtGiacNgu)

            val btnSave = view.findViewById<Button>(R.id.btnXacNhanOTP)
            btnSave.setOnClickListener {
                db.collection("users")
                    .document(user!!.uid)
                    .collection("sleep")
                    .add(
                        hashMapOf(
                            "giacngu" to edtGiacngu.text.toString(),
                            "timestamp" to System.currentTimeMillis()
                        )
                    )
                navController.popBackStack()
            }

            view
        }
    )
    }
}