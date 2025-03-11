package com.example.healthtrackingapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.healthtrackingapp.ui.components.BottomNavigation

@Composable
fun MainScreen(
    mainNavController: NavHostController
) {
    val navController = rememberNavController()

    Scaffold(
        contentWindowInsets = WindowInsets.systemBars,
        bottomBar = {
            BottomNavigation(
                navController = navController,
                mainNavController = mainNavController
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "dashboard",
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable("dashboard") { DashboardScreen() }
            composable("graph") { GraphScreen() }
            composable("notification") { NotificationScreen() }
            composable("user") { UserScreen() }
        }
    }
}

