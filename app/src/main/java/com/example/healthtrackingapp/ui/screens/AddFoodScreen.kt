package com.example.healthtrackingapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.healthtrackingapp.R
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFoodScreen(navController: NavHostController) {
    val db = FirebaseFirestore.getInstance()
    var user by remember { mutableStateOf(Firebase.auth.currentUser) }

    val scrollState = rememberScrollState()

    var selectedMealType by remember { mutableStateOf("") }
    var mealTypeExpanded by remember { mutableStateOf(false) }
    val mealTypes = listOf("Sáng", "Phụ sáng", "Trưa", "Phụ trưa", "Chiều", "Phụ chiều")

    var foodInput by remember { mutableStateOf("") }
    var vitaminInput by remember { mutableStateOf("") }
    var milkInput by remember { mutableStateOf("") }
    var supplementInput by remember { mutableStateOf("") }

    Scaffold(
        bottomBar = {
            BottomBarForAddFood(
                db = db,
                user = user!!,
                navController = navController,
                mealType = selectedMealType,
                foodInput = foodInput,
                vitaminInput = vitaminInput,
                milkInput = milkInput,
                supplementInput = supplementInput
            )
        },
        topBar = {
            TopAppBar(
                title = { Text("Nhập thông tin bữa ăn") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowDropDown, "Back")
                    }
                }
            )
        },
    ) { paddingValues ->
        Image(
            painter = painterResource(id = R.drawable.nen_app),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Chọn loại bữa ăn
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Chọn bữa ăn",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Box {
                        OutlinedTextField(
                            value = selectedMealType,
                            onValueChange = { },
                            modifier = Modifier.fillMaxWidth(),
                            readOnly = true,
                            label = { Text("Loại bữa ăn") },
                            trailingIcon = {
                                IconButton(onClick = { mealTypeExpanded = true }) {
                                    Icon(Icons.Default.ArrowDropDown, "Dropdown")
                                }
                            }
                        )

                        DropdownMenu(
                            expanded = mealTypeExpanded,
                            onDismissRequest = { mealTypeExpanded = false },
                            modifier = Modifier.fillMaxWidth(0.9f)
                        ) {
                            mealTypes.forEach { type ->
                                DropdownMenuItem(
                                    text = { Text(type) },
                                    onClick = {
                                        selectedMealType = type
                                        mealTypeExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // Nhập thông tin thức ăn
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Thông tin thực phẩm đã tiêu thụ",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.primary
                    )

                    OutlinedTextField(
                        value = foodInput,
                        onValueChange = { foodInput = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Thực phẩm đã ăn") },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences,
                            imeAction = ImeAction.Next
                        )
                    )
                }
            }

            // Nhập thông tin bổ sung
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Thông tin bổ sung",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.primary
                    )

                    OutlinedTextField(
                        value = vitaminInput,
                        onValueChange = { vitaminInput = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Vitamin (nếu có)") },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences,
                            imeAction = ImeAction.Next
                        )
                    )

                    OutlinedTextField(
                        value = milkInput,
                        onValueChange = { milkInput = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Sữa (nếu có)") },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences,
                            imeAction = ImeAction.Next
                        )
                    )

                    OutlinedTextField(
                        value = supplementInput,
                        onValueChange = { supplementInput = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Thuốc bổ (nếu có)") },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences,
                            imeAction = ImeAction.Done
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(80.dp)) // Không gian để tránh FAB che phủ nội dung
        }
    }
}

@Composable
private fun BottomBarForAddFood(
    db: FirebaseFirestore,
    user: FirebaseUser,
    navController: NavHostController,
    mealType: String,
    foodInput: String,
    vitaminInput: String,
    milkInput: String,
    supplementInput: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 40.dp, start = 30.dp, end = 30.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.Black)
            .clickable {
                db.collection("users")
                    .document(user.uid)
                    .collection("food")
                    .add(
                        hashMapOf(
                            "mealType" to mealType,
                            "foodInput" to foodInput,
                            "vitaminInput" to vitaminInput,
                            "milkInput" to milkInput,
                            "supplementInput" to supplementInput,
                            "timestamp" to System.currentTimeMillis(),
                        )
                    )
                navController.popBackStack()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "Save",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(vertical = 10.dp)
        )
    }
}

