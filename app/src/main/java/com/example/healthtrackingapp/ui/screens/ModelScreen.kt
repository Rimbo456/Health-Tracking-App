package com.example.healthtrackingapp.ui.screens

import android.content.Context
import android.widget.Toast
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
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.healthtrackingapp.R
import com.example.healthtrackingapp.ui.components.LineChart
import com.example.healthtrackingapp.ui.components.TopBarForAdd
import com.google.firebase.annotations.concurrent.Background

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
fun ModelScreen(navController: NavHostController) {
    Image(
        painter = painterResource(id = R.drawable.nen_app),
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize()
            .padding(),
        contentScale = ContentScale.Crop
    )
    val models = listOf(
        "Chuẩn đoán u não", "Bard", "Claude", "Gemini", "Llama"
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent) // Màu nền nhẹ
            .padding(horizontal = 16.dp)
    ) {
        TopBarAI(
            title = "Chẩn đoán vui vô cùng với AI"
        )
        Column(
            modifier = Modifier
        ) {
            AIModelCard(
                modelName = "Chuẩn đoán u não",
                navController = navController,
                route = "unao",
                background = painterResource(id = R.drawable.banner_unao)
            )
            Spacer(modifier = Modifier.height(20.dp))
            CommingSoonCard(
                modelName = "Bard",
                background = painterResource(id = R.drawable.commingsoon_ai),
                context = LocalContext.current
            )
        }
    }
}

@Composable
fun AIModelCard(
    modelName: String,
    navController: NavHostController,
    route: String,
    background: Painter,
) {
    Card(
        modifier = Modifier
            .shadow(elevation = 6.dp, shape = RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { navController.navigate(route) },
        shape = RoundedCornerShape(16.dp),
//        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(Color.Transparent)
    ) {
        Image(
            painter = background,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
    }
}

@Composable
fun CommingSoonCard(
    modelName: String,
    background: Painter,
    context: Context
) {
    Card(
        modifier = Modifier
            .shadow(elevation = 6.dp, shape = RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable {
                Toast.makeText(context, "Muốn dùng thì donate đi", Toast.LENGTH_SHORT).show()
            },
        shape = RoundedCornerShape(16.dp),
//        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(Color.Transparent)
    ) {
        Image(
            painter = background,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
    }
}


@Composable
private fun TopBarAI(
    navController: NavHostController? = null,
    title: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.08f)
            .background(color = Color.White, shape = RoundedCornerShape(20.dp)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}