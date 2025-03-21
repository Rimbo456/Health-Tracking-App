package com.example.healthtrackingapp.ui.components

import androidx.compose.foundation.Image
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.healthtrackingapp.R

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
                    Image(
                        painter = if (position == 1) painterResource(R.drawable.dashboard_icon_filled) else painterResource(R.drawable.dashboard_icon_outlined),
                        contentDescription = "Home",

                        modifier = Modifier.size(30.dp)
                    )
                    if (position == 1) {
                        Box(
                            modifier = Modifier
                                .padding(top = 5.dp)
                                .size(7.dp)
                                .clip(shape = CircleShape)
                                .background(Color(red = 87, green = 35, blue = 220))
                        )
                    }
                }
            }
            IconButton(
                onClick = {
                    position = 2
                    navController.navigate("graph")
                },
                modifier = Modifier.size(70.dp).padding(end = 10.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = if (position == 2) painterResource(R.drawable.ic_bot_touch_96) else painterResource(R.drawable.ic_bot_no_touch_96),
                        contentDescription = "ShoppingBag",

                        modifier = Modifier.size(30.dp)
                    )
                    if (position == 2) {
                        Box(
                            modifier = Modifier
                                .padding(top = 5.dp)
                                .size(7.dp)
                                .clip(shape = CircleShape)
                                .background(Color(red = 87, green = 35, blue = 220))
                        )
                    }
                }
            }
            IconButton(
                onClick = {
                    position = 5
                    mainNavController.navigate("healthbook")
                },
                modifier = Modifier
                    .size(65.dp)
                    .offset(y = -15.dp)
                    .scale(1.15f)
            ) {
                Image(
                    painter = painterResource(R.drawable.overview),
                    contentDescription = "Scan",
                    modifier = Modifier.fillMaxSize(),
                )
            }
            IconButton(
                onClick = {
                    position = 3
                    navController.navigate("notification")
                },
                modifier = Modifier.size(70.dp).padding(start = 10.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = if (position == 3) painterResource(R.drawable.ic_exercise_touch_96) else painterResource(R.drawable.ic_exercise_no_touch_96),
                        contentDescription = "Home",

                        modifier = Modifier.size(30.dp)
                    )
                    if (position == 3) {
                        Box(
                            modifier = Modifier
                                .padding(top = 5.dp)
                                .size(7.dp)
                                .clip(shape = CircleShape)
                                .background(Color(red = 87, green = 35, blue = 220))
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
                    Image(
                        painter = if (position == 4) painterResource(R.drawable.ic_account_touch_96) else painterResource(R.drawable.ic_account_no_touch_96),
                        contentDescription = "Home",

                        modifier = Modifier.size(40.dp)
                    )
                    if (position == 4) {
                        Box(
                            modifier = Modifier
                                .padding(top = 5.dp)
                                .size(7.dp)
                                .clip(shape = CircleShape)
                                .background(Color(red = 87, green = 35, blue = 220))
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