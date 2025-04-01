package com.example.healthtrackingapp.ui.screens

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.widget.TableRow
import android.widget.TextView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.healthtrackingapp.AlarmActivity
import com.example.healthtrackingapp.GioiThieuActivity
import com.example.healthtrackingapp.HoSoActivity
import com.example.healthtrackingapp.HoTroVaThongTinActivity
import com.example.healthtrackingapp.R
import com.example.healthtrackingapp.TinhTrangSucKhoeActivity
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

@Composable
fun UserScreen() {
    var user by remember { mutableStateOf(Firebase.auth.currentUser) }
    val db = FirebaseFirestore.getInstance()
    var name by remember { mutableStateOf("") }
    var isDataLoaded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        user?.uid?.let { uid ->
            db.collection("users")
                .document(uid)
                .get()
                .addOnSuccessListener { document ->
                    name = document.getString("name") ?: ""
                    isDataLoaded = true
                }
                .addOnFailureListener { e ->
                    Log.e("FirestoreError", "Error getting user data: ${e.message}")
                    isDataLoaded = true
                }
        }
    }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val view = LayoutInflater.from(context).inflate(R.layout.activity_main, null, false)

            val tvUserName = view.findViewById<TextView>(R.id.tvUserName)
            val tvEmail = view.findViewById<TextView>(R.id.tvEmail)
            val avt = view.findViewById<ShapeableImageView>(R.id.imgAvatar)

            val tbrHoSo = view.findViewById<TableRow>(R.id.tbrHoSo)
            val tbrTinhTrangSK = view.findViewById<TableRow>(R.id.tbrTinhTrangSK)
            val tbrHoTro = view.findViewById<TableRow>(R.id.tbrHoTro)
            val tbrVeChungToi = view.findViewById<TableRow>(R.id.tbrVeChungToi)
            tbrHoSo.setOnClickListener {
                val intent = Intent(context, HoSoActivity::class.java)
                context.startActivity(intent)
            }

            tbrTinhTrangSK.setOnClickListener {
                val intent = Intent(context, TinhTrangSucKhoeActivity::class.java)
                context.startActivity(intent)
            }

            tbrHoTro.setOnClickListener {
                val intent = Intent(context, HoTroVaThongTinActivity::class.java)
                context.startActivity(intent)
            }

            tbrVeChungToi.setOnClickListener {
                val intent = Intent(context, GioiThieuActivity::class.java)
                context.startActivity(intent)
            }
            view
        },
        update = { view ->
            // Cập nhật UI sau khi dữ liệu được tải
            val tvUserName = view.findViewById<TextView>(R.id.tvUserName)
            val tvEmail = view.findViewById<TextView>(R.id.tvEmail)

            if (user != null) {
                tvUserName.text = name
                tvEmail.text = user!!.email
            }
        }
    )
}