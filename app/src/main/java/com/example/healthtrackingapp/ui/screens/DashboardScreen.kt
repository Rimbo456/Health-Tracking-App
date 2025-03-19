package com.example.healthtrackingapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.healthtrackingapp.R
import com.example.healthtrackingapp.ui.components.CardDashboard
import com.example.healthtrackingapp.ui.components.ItemGoal
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun DashboardScreen(
    navController: NavHostController,
    modifier: Modifier? = null,
) {
    var textToShow by remember { mutableStateOf("") }
    val fullText = "Hôm nay bạn cảm thấy thế nào?"
    val coroutineScope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            fullText.forEachIndexed { index, _ ->
                textToShow = fullText.substring(0, index + 1)
                delay(50)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.nen_app),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight(0.15f)
                .fillMaxWidth(),
        ) {
            val calendar = Calendar.getInstance()
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
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
        Row(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(56.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp)
                .clickable { showDialog = true },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Thêm",
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = textToShow,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )
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
                    onClick = { navController.navigate("goalscreen") },
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
    if (showDialog) {
        FeelingDialog(onDismiss = { showDialog = false })
    }
}

@Composable
fun FeelingDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.White, shape = RoundedCornerShape(16.dp))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Hôm nay bạn cảm thấy thế nào?",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    listOf("😃", "😊", "😐", "😞", "😢").forEach { emoji ->
                        Text(
                            text = emoji,
                            fontSize = 32.sp,
                            modifier = Modifier.clickable { onDismiss() }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onDismiss) {
                    Text("Đóng")
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun DashboardScreenPreview() {
}