package com.example.healthtrackingapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.healthtrackingapp.R
import com.example.healthtrackingapp.ui.components.BottomBarForAdd
import com.example.healthtrackingapp.ui.components.TopBarForAdd

@Composable
fun BloodPressureScreen(
    navController: NavHostController,
) {
    var tamthu by remember { mutableStateOf("") }
    var tamtruong by remember { mutableStateOf("") }
    var nhiptim by remember { mutableStateOf("") }
    
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
                painter = painterResource(id = R.drawable.reshot_icon_blood_pressure_zvjhstxe8u),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight(0.2f)
                    .fillMaxWidth()
            )
            Text(
                text = "Nhap so do huyet ap",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 20.dp),
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .weight(0.3f)
                            .shadow(elevation = 10.dp, shape = RoundedCornerShape(20.dp))
                            .clip(RoundedCornerShape(20.dp))
                            .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(20.dp))
                            .background(Color.White)
                            .padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        BasicTextField(
                            value = tamthu,
                            onValueChange = {
                                if (it.length <= 2) {
                                    tamthu = it
                                }
                            },
                            singleLine = true,
                            textStyle = TextStyle(fontSize = 30.sp, textAlign = TextAlign.Start),
                            modifier = Modifier
                                .width(IntrinsicSize.Min)
                                .padding(vertical = 18.dp,)
                        )
                        Text(text = "Tam thu")
                    }
                    Column(
                        modifier = Modifier
                            .weight(0.3f)
                            .shadow(elevation = 10.dp, shape = RoundedCornerShape(20.dp))
                            .clip(RoundedCornerShape(20.dp))
                            .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(20.dp))
                            .background(Color.White)
                            .padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        BasicTextField(
                            value = tamtruong,
                            onValueChange = {
                                if (it.length <= 2) {
                                    tamtruong = it
                                }
                            },
                            singleLine = true,
                            textStyle = TextStyle(fontSize = 30.sp, textAlign = TextAlign.Start),
                            modifier = Modifier
                                .width(IntrinsicSize.Min)
                                .padding(vertical = 18.dp, horizontal = 20.dp)
                        )
                        Text(text = "Tam truong")
                    }
                    Column(
                        modifier = Modifier
                            .weight(0.3f)
                            .shadow(elevation = 10.dp, shape = RoundedCornerShape(20.dp))
                            .clip(RoundedCornerShape(20.dp))
                            .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(20.dp))
                            .background(Color.White)
                            .padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        BasicTextField(
                            value = nhiptim,
                            onValueChange = {
                                if (it.length <= 2) {
                                    nhiptim = it
                                }
                            },
                            singleLine = true,
                            textStyle = TextStyle(fontSize = 30.sp, textAlign = TextAlign.Start),
                            modifier = Modifier
                                .width(IntrinsicSize.Min)
                                .padding(vertical = 18.dp, horizontal = 20.dp)
                        )
                        Text(text = "Nhip tim")
                    }
                }
            }
        }
    }
}