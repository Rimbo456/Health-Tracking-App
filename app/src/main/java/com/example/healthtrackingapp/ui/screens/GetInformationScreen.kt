package com.example.healthtrackingapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.healthtrackingapp.R
import com.example.healthtrackingapp.ui.components.SliderCustom

@Composable
fun GetInformationScreen(navController: NavHostController) {
    var height by remember { mutableStateOf(50f) }
    var weighth by remember { mutableStateOf(20f) }
    Scaffold() { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier.padding(start = 40.dp, end = 40.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.giveinfor),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 20.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Card(
                            onClick = { /*TODO*/ }, colors = CardDefaults.cardColors(
                                Color(
                                    red = 216, green = 216, blue = 216, alpha = 255
                                )
                            ), border = BorderStroke(
                                width = 2.dp, color = Color(
                                    red = 216, green = 216, blue = 216, alpha = 255
                                )
                            )
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.businessman_man_business_1),
                                contentDescription = null,
                                modifier = Modifier.size(120.dp)
                            )
                        }
                        Text(
                            text = stringResource(id = R.string.male),
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(top = 10.dp)
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Card(
                            onClick = { /*TODO*/ }, colors = CardDefaults.cardColors(
                                Color.White
                            ), border = BorderStroke(
                                width = 2.dp, color = Color(
                                    red = 216, green = 216, blue = 216, alpha = 255
                                )
                            )
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.businesswoman_business_woman_working_girl_1),
                                contentDescription = null,
                                modifier = Modifier.size(120.dp)
                            )
                        }
                        Text(
                            text = stringResource(id = R.string.female),
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(top = 10.dp)
                        )
                    }
                }
                SliderCustom(
                    sliderPosition = height,
                    onValueChange = { height = it },
                    valueRange = 50f..300f,
                    unit = "cm",
                    title = stringResource(id = R.string.height)
                )
                SliderCustom(
                    sliderPosition = weighth,
                    onValueChange = { weighth = it },
                    valueRange = 20f..80f,
                    unit = "kg",
                    title = stringResource(id = R.string.weight),
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    IconButton(
                        onClick = { navController.navigate("start") },
                        colors = IconButtonDefaults.iconButtonColors(Color.Black),
                        modifier = Modifier.size(56.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                    IconButton(
                        onClick = {
                            navController.navigate("main") {
                                popUpTo(0) { inclusive = true }
                            }
                        },
                        colors = IconButtonDefaults.iconButtonColors(Color.Black),
                        modifier = Modifier.size(56.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun GetInformationScreenPreview() {
}