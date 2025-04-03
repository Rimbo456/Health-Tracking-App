package com.example.healthtrackingapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.captionBar
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.isCaptionBarVisible
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.healthtrackingapp.R
import com.example.healthtrackingapp.ui.components.ItemBook
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun HealthBookScreen(navController: NavHostController) {
    val uid = FirebaseAuth.getInstance().currentUser?.uid
    val db = FirebaseFirestore.getInstance()

    var weight by remember { mutableStateOf(0.0) }

    LaunchedEffect(uid) {
        db.collection("users")
            .document(uid!!)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    weight = document.getDouble("weight")!!
                } else println("User không tồn tại!")
            }
    }

    Scaffold(
        modifier = Modifier.padding(WindowInsets.systemBars.asPaddingValues()),
        topBar = { TopBar(navController, title = stringResource(R.string.suckhoecuaban)) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Image(
                painter = painterResource(id = R.drawable.nen_app),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .verticalScroll(rememberScrollState())
                        .padding(vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    ItemBook(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.White,
                        title = stringResource(R.string.meal),
                        image = R.drawable.ic_hot_dog_100,
                        navController = navController,
                        route = "addfood",
                        mota = stringResource(R.string.motabuan)
                    )
                    ItemBook(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.White,
                        title = stringResource(R.string.weight),
                        image = R.drawable.ic_weight_100,
                        navController = navController,
                        route = "weighingscreen",
                        value = ((weight * 100).toInt() / 100f).toString(),
                        unit = "kg",
                        mota = stringResource(R.string.motacannang)
                    )
                    ItemBook(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.White,
                        title = stringResource(R.string.sleep),
                        image = R.drawable.ic_sleep_100,
                        navController = navController,
                        route = "addsleep",
                        mota = stringResource(R.string.motagiacngu)
                    )
                    ItemBook(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.White,
                        title = stringResource(R.string.water),
                        image = R.drawable.ic_water_100,
                        navController = navController,
                        route = "addwater",
                        mota = stringResource(R.string.motaluongnuoc)
                    )
                    ItemBook(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.White,
                        title = "Huyết áp",
                        image = R.drawable.ic_sphygmomanometer_100,
                        navController = navController,
                        route = "bloodpressurescreen",
                        mota = stringResource(R.string.motahuyetap)
                    )
                    ItemBook(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.White,
                        title = stringResource(R.string.temperature),
                        image = R.drawable.ic_warm_100,
                        navController = navController,
                        route = "addtemperaturescreen",
                        mota = stringResource(R.string.motanhietdo)
                    )
                    ItemBook(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.White,
                        title = stringResource(R.string.glucose),
                        image = R.drawable.ic_glucometer_100,
                        navController = navController,
                        route = "addbloodglucose",
                        mota = stringResource(R.string.motaduonghuyet)
                    )
                    ItemBook(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.White,
                        title = stringResource(R.string.blood_oxy),
                        image = R.drawable.ic_pulse_oximeter_100,
                        navController = navController,
                        route = "addoxygen",
                        mota = stringResource(R.string.motaoxy)
                    )
                }
            }
        }
    }
}

@Composable
fun TopBar(
    navController: NavHostController? = null,
    title: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.08f)
            .background(color = Color.Transparent, shape = RoundedCornerShape(20.dp)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = {
                if (navController != null) {
                    navController.navigate("main")
                }
            }) {
                Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = null)
            }
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            IconButton(
                onClick = {
                    if (navController != null) {
                        navController.navigate("overviewscreen")
                    }
                },
//                colors = IconButtonDefaults.iconButtonColors(Color.White),
                modifier = Modifier.size(55.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sotay),
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                )
            }
        }
    }
}