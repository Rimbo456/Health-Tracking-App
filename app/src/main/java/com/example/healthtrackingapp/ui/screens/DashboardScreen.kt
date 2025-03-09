package com.example.healthtrackingapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthtrackingapp.R
import com.example.healthtrackingapp.ui.components.CardDashboard
import com.example.healthtrackingapp.ui.components.ItemGoal
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun DashboardScreen(
    modifier: Modifier? = null
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight(0.15f)
                .fillMaxWidth(),
        ){
            val calendar = Calendar.getInstance()
            val dateFormat = SimpleDateFormat("dd/MM/yyyy",Locale.getDefault())
            val currentDate = dateFormat.format(calendar.time)
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Welcome",
                    fontFamily = FontFamily.Cursive,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = currentDate,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.goal),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Filled.List,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.95f),
                verticalArrangement = Arrangement.Top
            ) {
                Card(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(15.dp)
                    ) {
                        Text(
                            text = "Tittle",
                            fontSize = 25.sp,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )
                        Text(
                            text = "Content",
                            fontSize = 18.sp,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )
                        Text(
                            text = "Date",
                            fontSize = 18.sp,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )
                    }
                }
//                Text(text = stringResource(id = R.string.notarget))
            }
        }
        Text(
            text = stringResource(id = R.string.overview),
            fontSize = 26.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth(0.9f)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight(0.6f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CardDashboard(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    title = stringResource(id = R.string.heartrate),
                    value = 80,
                    unit = "bpm",
                    icon = Icons.Filled.Favorite,
                    color = CardDefaults.cardColors(
                        Color(red = 255, green = 226, blue = 226, alpha = 255)
                    ),
                    textColor = Color(red = 189, green = 46, blue = 75, alpha = 255)
                )
                Spacer(modifier = Modifier.width(10.dp))
                CardDashboard(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    title = stringResource(id = R.string.exercise),
                    value = 24,
                    unit = "min",
                    icon = Icons.Filled.ElectricBolt,
                    color = CardDefaults.cardColors(
                        Color(red = 254, green = 224, blue = 255, alpha = 255)
                    ),
                    textColor = Color(red = 111, green = 40, blue = 182, alpha = 255)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CardDashboard(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    title = stringResource(id = R.string.walking),
                    value = 10,
                    unit = "km",
                    icon = Icons.Filled.Flag,
                    color = CardDefaults.cardColors(
                        Color(red = 203, green = 248, blue = 248, alpha = 255)
                    ),
                    textColor = Color(red = 59, green = 155, blue = 131, alpha = 255)
                )
                Spacer(modifier = Modifier.width(10.dp))
                CardDashboard(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    title = stringResource(id = R.string.sleep),
                    value = 8,
                    unit = "hrs",
                    icon = Icons.Filled.Hotel,
                    color = CardDefaults.cardColors(
                        Color(red = 207, green = 223, blue = 255, alpha = 255)
                    ),
                    textColor = Color(red = 41, green = 96, blue = 155, alpha = 255)
                )
            }
        }

    }
}

@Preview(showSystemUi = true)
@Composable
fun DashboardScreenPreview() {
    DashboardScreen()
}