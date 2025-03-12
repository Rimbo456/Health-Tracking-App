package com.example.healthtrackingapp.ui.screens

import android.view.LayoutInflater
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.healthtrackingapp.R

@Composable
fun UserScreen() {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            LayoutInflater.from(context).inflate(R.layout.activity_main, null, false)
        }
    )
}