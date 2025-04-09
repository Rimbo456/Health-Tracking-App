package com.example.healthtrackingapp.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.healthtrackingapp.R
import com.example.healthtrackingapp.ui.components.ItemGoal
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Goal(
    val id: String = "",
    val title: String = "",
    val content: String = "",
    val date: String = "",
    val isCompleted: Boolean = false
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GoalScreen(navController: NavHostController) {
    var showDialog by remember { mutableStateOf(false) }
    var showDialogDelete by remember { mutableStateOf(false) }
    var listGoals by remember { mutableStateOf(listOf<Goal>()) }
    var refresh by remember { mutableStateOf(0) }
    var tempID by remember { mutableStateOf("") }

    val db = FirebaseFirestore.getInstance()
    var user by remember { mutableStateOf(Firebase.auth.currentUser) }

    LaunchedEffect(refresh) {
        db.collection("users")
            .document(user!!.uid)
            .collection("goals")
            .get()
            .addOnSuccessListener { result ->
                val newGoals = mutableListOf<Goal>()

                for (document in result) {

                    // Chuyển đổi Firestore document thành đối tượng Goal
                    val goal = Goal(
                        id = document.id,
                        title = document.getString("title") ?: "",
                        content = document.getString("content") ?: "",
                        date = document.getString("date") ?: "",
                        isCompleted = document.getBoolean("isCompleted") ?: false
                    )
                    newGoals.add(goal)
                }

                // Cập nhật danh sách với tất cả các mục tiêu
                listGoals = newGoals
            }
    }

    Scaffold(
        topBar = { TopBar(navController, "Mục tiêu") },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showDialog = true
                },
                containerColor = androidx.compose.ui.graphics.Color.Black,
                contentColor = androidx.compose.ui.graphics.Color.White,
                modifier = Modifier.size(60.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null,
                    modifier = Modifier.padding(16.dp)
                )
            }
        },
        modifier = Modifier.padding(WindowInsets.systemBars.asPaddingValues())
    ) { innerPadding ->
        Image(
            painter = painterResource(id = R.drawable.nen_app),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentScale = ContentScale.Crop
        )
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(listGoals) { goal ->
                if (goal.isCompleted) {
                    ItemGoal(
                        content = goal.content,
                        title = goal.title,
                        date = goal.date,
                        isChecked = true,
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .combinedClickable(
                                onClick = {},
                                onLongClick = {
                                    showDialogDelete = true
                                    tempID = goal.id
                                }
                            ),
                    )
                } else {
                    ItemGoal(
                        content = goal.content,
                        title = goal.title,
                        date = goal.date,
                        isChecked = false,
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .combinedClickable(
                                onClick = {},
                                onLongClick = {
                                    showDialogDelete = true
                                    tempID = goal.id
                                }
                            ),
                    )
                }
            }
        }
    }
    GoalDialog(
        showDialog = showDialog,
        onDismiss = { showDialog = false },
        onSaveSuccess = { refresh++ }
    )
    DialogDelete(
        showDialog = showDialogDelete,
        onDismiss = { showDialogDelete = false },
        onDelete = { refresh++ },
        docId = tempID,
        onComplete = {refresh++}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onSaveSuccess: () -> Unit
) {
    if (showDialog) {
        val db = FirebaseFirestore.getInstance()
        var user by remember { mutableStateOf(Firebase.auth.currentUser) }

        var title by remember { mutableStateOf("") }
        var content by remember { mutableStateOf("") }
        var date by remember { mutableStateOf(Date()) }
        var showDatePicker by remember { mutableStateOf(false) }

        val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        Dialog(onDismissRequest = onDismiss) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Thêm Mục Tiêu Mới",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Tiêu đề mục tiêu") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = content,
                        onValueChange = { content = it },
                        label = { Text("Nội dung mục tiêu") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = dateFormatter.format(date),
                        onValueChange = { /* Giá trị nhập sẽ được xử lý qua DatePicker */ },
                        label = { Text("Ngày hoàn thành") },
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = { showDatePicker = true }) {
                                Icon(
                                    imageVector = androidx.compose.material.icons.Icons.Default.DateRange,
                                    contentDescription = "Chọn ngày"
                                )
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = onDismiss) {
                            Text("Hủy")
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = {
                                db.collection("users")
                                    .document(user!!.uid)
                                    .collection("goals")
                                    .add(
                                        hashMapOf(
                                            "title" to title,
                                            "content" to content,
                                            "date" to dateFormatter.format(date),
                                            "isCompleted" to false
                                        )
                                    )
                                onDismiss()
                                onSaveSuccess()
                            },
                            enabled = title.isNotBlank() && content.isNotBlank()
                        ) {
                            Text("Lưu")
                        }
                    }
                }
            }
        }

        // DatePicker Dialog
        if (showDatePicker) {
            val datePickerState = rememberDatePickerState(
                initialSelectedDateMillis = date.time
            )

            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            datePickerState.selectedDateMillis?.let {
                                date = Date(it)
                            }
                            showDatePicker = false
                        }
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Hủy")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }
}

@Composable
fun DialogDelete(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onDelete: () -> Unit,
    onComplete: () -> Unit,
    docId: String,
) {
    if (showDialog) {
        val db = FirebaseFirestore.getInstance()
        var user by remember { mutableStateOf(Firebase.auth.currentUser) }
        Dialog(onDismissRequest = onDismiss) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Xác nhận thao tác", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Bạn muốn làm gì với mục này?", textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        TextButton(onClick = onDismiss) {
                            Text("Hủy")
                        }
                        TextButton(onClick = {
                            db.collection("users")
                                .document(user!!.uid)
                                .collection("goals")
                                .document(docId)
                                .delete()
                                .addOnSuccessListener {
                                    onDismiss()
                                    onDelete()
                                }
                        }) {
                            Text("Xóa", color = Color.Red)
                        }
                        TextButton(onClick = {
                            db.collection("users")
                                .document(user!!.uid)
                                .collection("goals")
                                .document(docId)
                                .update("isCompleted", true)
                                .addOnSuccessListener {
                                    onComplete()
                                    onDismiss()
                                }
                        }) {
                            Text("Hoàn thành", color = Color.Green)
                        }
                    }
                }
            }
        }
    }
}
