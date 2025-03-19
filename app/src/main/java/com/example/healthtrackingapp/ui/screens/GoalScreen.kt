package com.example.healthtrackingapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.healthtrackingapp.ui.components.ItemGoal

@Composable
fun GoalScreen(navController: NavHostController) {
    Scaffold(
        topBar = { TopBar(navController, "Goal") },
        modifier = Modifier.padding(WindowInsets.systemBars.asPaddingValues())
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ItemGoal(content = "Hello", isChecked = false, modifier = Modifier.fillMaxWidth(0.9f))
            ItemGoal(content = "Hello", isChecked = false, modifier = Modifier.fillMaxWidth(0.9f))
            ItemGoal(content = "Hello", isChecked = false, modifier = Modifier.fillMaxWidth(0.9f))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun GoalScreenPreview() {
}