package com.example.healthtrackingapp.ui.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryUNao(navController: NavHostController? = null) {
    val context = LocalContext.current
    var imageFilePairs by remember { mutableStateOf(getImageFilesFromAppDir(context)) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var imgToDelete by remember { mutableStateOf<File?>(null) }
    var txtToDelete by remember { mutableStateOf<File?>(null) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Lịch sử ảnh") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController?.popBackStack()
                        }
                    ) {
                        Icon(
                            Icons.Default.ArrowBackIosNew,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            if (imageFilePairs.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Chưa có hình ảnh lưu trữ", fontSize = 16.sp, color = Color.Gray)
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize().padding(12.dp)) {
                    items(imageFilePairs, key = { it.first.absolutePath }) { filePair ->
                        ImageItem(
                            imageFile = filePair.first,
                            textFile = filePair.second,
                            onDeleteClick = {
                                imgToDelete = filePair.first
                                txtToDelete = filePair.second
                                showDeleteDialog = true
                            }
                        )
                    }
                }
            }
        }
    }

    if (showDeleteDialog && imgToDelete != null) {
        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
                imgToDelete = null
                txtToDelete = null
            },
            title = { Text("Xác nhận xóa") },
            text = { Text("Bạn có chắc chắn muốn xóa hình ảnh này không?") },
            confirmButton = {
                Button(onClick = {
                    imgToDelete?.let { file ->
                        if (file.delete()) {
                            imageFilePairs = getImageFilesFromAppDir(context)
                        }
                    }
                    showDeleteDialog = false
                    imgToDelete = null
                    txtToDelete?.let { file ->
                        if (file.delete()) {
                            imageFilePairs = getImageFilesFromAppDir(context)
                        }
                    }
                    showDeleteDialog = false
                    txtToDelete = null
                }) {
                    Text("Xóa")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDeleteDialog = false
                    imgToDelete = null
                    txtToDelete = null
                }) {
                    Text("Hủy")
                }
            }
        )
    }
}

@Composable
fun ImageItem(
    imageFile: File,
    textFile: File?,
    onDeleteClick: () -> Unit
) {
    val date = SimpleDateFormat("dd/MM/yyyy - HH:mm", Locale.getDefault()).format(Date(imageFile.lastModified()))
    val textContent = textFile?.readText() ?: "Không có nội dung liên quan"

    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = rememberAsyncImagePainter(imageFile),
                contentDescription = "Hình ảnh đã lưu",
                modifier = Modifier.fillMaxWidth().height(200.dp).background(Color.Gray)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = date, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = textContent, fontSize = 14.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(onClick = onDeleteClick) {
                    Text("Xóa", color = Color.Red)
                }
            }
        }
    }
}

fun getImageFilesFromAppDir(context: Context): List<Pair<File, File?>> {
    val dir = context.filesDir
    val imageFiles = dir.listFiles { _, name -> name.endsWith(".jpg") || name.endsWith(".png") }
        ?.sortedByDescending { it.lastModified() } ?: emptyList()

    return imageFiles.map { imageFile ->
        val textFile = File(dir, imageFile.nameWithoutExtension + ".txt")
        imageFile to if (textFile.exists()) textFile else null
    }
}
