package com.example.healthtrackingapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlusOne
import androidx.compose.material.icons.outlined.AutoGraph
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PlusOne
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun BottomNavigation(
    navController: NavHostController,
    mainNavController: NavHostController,
) {

    var position by remember { mutableStateOf(1) }

    Box(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.navigationBars)
            .fillMaxWidth()

    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
        ) {
            IconButton(
                onClick = {
                    position = 1
                    navController.navigate("dashboard")
                },
                modifier = Modifier.size(70.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        if (position == 1) Icons.Filled.Category else Icons.Outlined.Category,
                        contentDescription = "Home",
                        tint = if (position == 1) Color.Black else Color.Gray,
                        modifier = Modifier.size(30.dp)
                    )
                    if (position == 1) {
                        Box(
                            modifier = Modifier
                                .padding(top = 5.dp)
                                .size(7.dp)
                                .clip(shape = CircleShape)
                                .background(Color.Black)
                        )
                    }
                }
            }
            IconButton(
                onClick = {
                    position = 2
                    navController.navigate("graph")
                },
                modifier = Modifier.size(70.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        if (position == 2) Icons.Filled.AutoGraph else Icons.Outlined.AutoGraph,
                        contentDescription = "ShoppingBag",
                        tint = if (position == 2) Color.Black else Color.Gray,
                        modifier = Modifier.size(30.dp)
                    )
                    if (position == 2) {
                        Box(
                            modifier = Modifier
                                .padding(top = 5.dp)
                                .size(7.dp)
                                .clip(shape = CircleShape)
                                .background(Color.Black)
                        )
                    }
                }
            }
            IconButton(
                onClick = {
                    position = 5
                    mainNavController.navigate("healthbook")
                },
                colors = IconButtonDefaults.iconButtonColors(Color.Black),
                modifier = Modifier.size(65.dp)
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "Scan",
                    tint = Color.LightGray,
                    modifier = Modifier.size(35.dp)
                )
            }
            IconButton(
                onClick = {
                    position = 3
                    navController.navigate("notification")
                },
                modifier = Modifier.size(70.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        if (position == 3) Icons.Filled.Notifications else Icons.Outlined.Notifications,
                        contentDescription = "Home",
                        tint = if (position == 3) Color.Black else Color.Gray,
                        modifier = Modifier.size(30.dp)
                    )
                    if (position == 3) {
                        Box(
                            modifier = Modifier
                                .padding(top = 5.dp)
                                .size(7.dp)
                                .clip(shape = CircleShape)
                                .background(Color.Black)
                        )
                    }
                }
            }
            IconButton(
                onClick = {
                    position = 4
                    navController.navigate("user")
                },
                modifier = Modifier.size(70.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        if (position == 4) Icons.Filled.Person else Icons.Outlined.Person,
                        contentDescription = "Home",
                        tint = if (position == 4) Color.Black else Color.Gray,
                        modifier = Modifier.size(40.dp)
                    )
                    if (position == 4) {
                        Box(
                            modifier = Modifier
                                .padding(top = 5.dp)
                                .size(7.dp)
                                .clip(shape = CircleShape)
                                .background(Color.Black)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationPreview() {
}