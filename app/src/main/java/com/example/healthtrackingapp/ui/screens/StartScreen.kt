package com.example.healthtrackingapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.healthtrackingapp.R

@Composable
fun StartScreen(navController: NavHostController? = null) {
    Scaffold(
        contentWindowInsets = WindowInsets.systemBars,
        floatingActionButton = {
            IconButton(
                onClick = {
                    if (navController != null) {
                        navController.navigate("getinfor")
                    }
                },
                colors = IconButtonDefaults.iconButtonColors(Color.Black),
                modifier = Modifier.size(56.dp)
            ) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Color.White)
            }
        }
        ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    Modifier.fillMaxWidth(0.85f),
                    horizontalAlignment = Alignment.End
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.layer_2),
                        contentDescription = null,
                        modifier = Modifier
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.group_6),
                    contentDescription = null,
                    modifier = Modifier.padding(start = 30.dp)
                )
                Text(
                    text = stringResource(id = R.string.welcome_text),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(start = 30.dp, top = 40.dp, end = 30.dp)
                )
            }
            Box(
                modifier = Modifier
                    .background(color = Color(red = 220, green = 220, blue = 221, alpha = 255))
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun FloatingButton() {
    Button(onClick = { /*TODO*/ }) {
        Text(text = "Start")
    }
}

@Preview(showSystemUi = true)
@Composable
fun StartScreenPreview() {
    StartScreen()
}