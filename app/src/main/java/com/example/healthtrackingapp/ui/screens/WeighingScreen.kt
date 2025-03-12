package com.example.healthtrackingapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.healthtrackingapp.R
import com.example.healthtrackingapp.ui.components.BottomBarForAdd
import com.example.healthtrackingapp.ui.components.TopBarForAdd

@Composable
fun WeighingScreen(navController: NavHostController) {
    var chiso by remember { mutableStateOf("") }
    var unitIndex by remember { mutableStateOf(1) }

    Scaffold(
        modifier = Modifier.padding(WindowInsets.systemBars.asPaddingValues()),
        topBar = { TopBarForAdd(navController) },
        bottomBar = { BottomBarForAdd() }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.05f))
            Image(
                painter = painterResource(id = R.drawable.reshot_icon_weighing_scale_pzrhsgv7y6),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight(0.2f)
                    .fillMaxWidth()
            )
            Text(
                text = "Nhap can nang cua ban",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 20.dp),
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .height(60.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.LightGray)
            ) {
                BasicTextField(
                    value = chiso,
                    onValueChange = { chiso = it },
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 20.sp, textAlign = TextAlign.Start),
                    modifier = Modifier
                        .weight(0.35f)
                        .padding(vertical = 18.dp, horizontal = 20.dp)
                )
                Row(
                    modifier = Modifier
                        .weight(0.65f)
                        .fillMaxHeight()
                        .padding(5.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        modifier = Modifier.fillMaxWidth(0.9f)
                    ) {
                        Card(
                            onClick = { unitIndex = 1 },
                            colors = if (unitIndex == 1) CardDefaults.cardColors() else CardDefaults.cardColors(Color.White),
                            modifier = if (unitIndex == 1) Modifier.weight(0.4f) else Modifier.weight(0.3f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 10.dp, vertical = 5.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "kg",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth())
                            }
                        }
                        Card(
                            onClick = { unitIndex = 2 },
                            colors = if (unitIndex == 2) CardDefaults.cardColors() else CardDefaults.cardColors(Color.White),
                            modifier = if (unitIndex == 2) Modifier.weight(0.4f) else Modifier.weight(0.3f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 10.dp, vertical = 5.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "lbs",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth())
                            }
                        }
                        Card(
                            onClick = { unitIndex = 3 },
                            colors = if (unitIndex == 3) CardDefaults.cardColors() else CardDefaults.cardColors(Color.White),
                            modifier = if (unitIndex == 3) Modifier.weight(0.4f) else Modifier.weight(0.3f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 10.dp, vertical = 5.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "st",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth())
                            }
                        }
                    }
                }
            }
        }
    }
}



