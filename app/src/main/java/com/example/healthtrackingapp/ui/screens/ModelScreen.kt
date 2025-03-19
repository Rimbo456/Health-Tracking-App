package com.example.healthtrackingapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.captionBar
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

//@Composable
//fun ModelScreen() {
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.spacedBy(10.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Card(
//            colors = CardDefaults.cardColors(),
//            elevation = CardDefaults.cardElevation(5.dp),
//            shape = RoundedCornerShape(30.dp),
//            modifier = Modifier,
////        border = BorderStroke(width = 2.dp, color = Color.Black)
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .clickable { },
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(17.dp),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Row(
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
////                    Image(
////                        painter = painterResource(id = null),
////                        contentDescription = null,
////                        modifier = Modifier
////                            .size(35.dp)
////                            .padding(end = 5.dp)
////                    )
//                        Text(
//                            text = "title",
//                            fontSize = 16.sp,
//                            fontWeight = FontWeight.Bold
//                        )
//                    }
//                    Row(
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Text(
//                            text = "khong on",
//                            fontSize = 16.sp,
//                            modifier = Modifier.padding(end = 5.dp)
//                        )
//                        Icon(
//                            imageVector = Icons.Default.ArrowForwardIos,
//                            contentDescription = null
//                        )
//                    }
//                }
//                Box(
//                    modifier = Modifier
//                        .height(1.dp)
//                        .fillMaxWidth()
//                        .border(1.dp, Color.Gray)
//                )
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(17.dp),
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    Text(text = "Khong co du lieu")
//                    Text(text = "Time")
//                }
//            }
//        }
//        Card(
//            colors = CardDefaults.cardColors(),
//            elevation = CardDefaults.cardElevation(5.dp),
//            shape = RoundedCornerShape(30.dp),
//            modifier = Modifier,
////        border = BorderStroke(width = 2.dp, color = Color.Black)
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .clickable { },
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(17.dp),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Row(
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
////                    Image(
////                        painter = painterResource(id = null),
////                        contentDescription = null,
////                        modifier = Modifier
////                            .size(35.dp)
////                            .padding(end = 5.dp)
////                    )
//                        Text(
//                            text = "title",
//                            fontSize = 16.sp,
//                            fontWeight = FontWeight.Bold
//                        )
//                    }
//                    Row(
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Text(
//                            text = "khong on",
//                            fontSize = 16.sp,
//                            modifier = Modifier.padding(end = 5.dp)
//                        )
//                        Icon(
//                            imageVector = Icons.Default.ArrowForwardIos,
//                            contentDescription = null
//                        )
//                    }
//                }
//                Box(
//                    modifier = Modifier
//                        .height(1.dp)
//                        .fillMaxWidth()
//                        .border(1.dp, Color.Gray)
//                )
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(17.dp),
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    Text(text = "Khong co du lieu")
//                    Text(text = "Time")
//                }
//            }
//        }
//    }
//}
@Composable
fun ModelScreen(navController: NavController) {
    val models = listOf(
        "ChatGPT", "Bard", "Claude", "Gemini", "Llama"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)) // Màu nền nhẹ
            .padding(16.dp)
    ) {
        items(models) { model ->
            AIModelCard(model) {
                navController.navigate("model_detail/$model")
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun AIModelCard(modelName: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .background(Color.White)
            .shadow(6.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color(0xFF6200EE), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Computer,
                        contentDescription = "AI Model",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = modelName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Go",
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}


