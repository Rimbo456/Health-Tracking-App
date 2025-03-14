package com.example.healthtrackingapp.ui.screens

import android.content.Intent
import android.view.LayoutInflater
import android.widget.TableRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.healthtrackingapp.HoSoActivity
import com.example.healthtrackingapp.R

@Composable
fun UserScreen() {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val view = LayoutInflater.from(context).inflate(R.layout.activity_main, null, false)

            val tbrHoSo = view.findViewById<TableRow>(R.id.tbrHoSo)
            tbrHoSo.setOnClickListener {
                val intent = Intent(context, HoSoActivity::class.java)
                context.startActivity(intent)
            }
            view
        }
    )
}