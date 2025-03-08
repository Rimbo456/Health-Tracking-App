package com.example.healthtrackingapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.healthtrackingapp.ui.components.ItemNof

@Composable
fun NotificationScreen() {
    Column {
        Box(modifier = Modifier.weight(0.4f))
        Column(
            modifier = Modifier
                .weight(0.6f)
        ) {
            ItemNof()
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun NotificantScreenPreview() {
    NotificationScreen()
}