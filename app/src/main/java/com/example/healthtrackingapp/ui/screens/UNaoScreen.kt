package com.example.healthtrackingapp.ui.screens

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.healthtrackingapp.data.serverUNao.ApiServiceUNao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import coil.compose.rememberAsyncImagePainter
import com.example.healthtrackingapp.R
import com.example.healthtrackingapp.ui.components.TopBarForAdd
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// 2. Tạo Retrofit client một cách tốt hơn (thêm vào file khác)
object RetrofitClient {
    private const val BASE_URL = "http://192.168.1.14:5000"

    val apiService: ApiServiceUNao by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServiceUNao::class.java)
    }
}

// 3. Sửa hàm uploadImage để sử dụng coroutines tốt hơn
suspend fun uploadImage(apiService: ApiServiceUNao, uri: Uri, context: Context): Pair<Uri?, String?> {
    val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    return withContext(Dispatchers.IO) {
        try {
            Log.d("UPLOAD", "Bắt đầu tải ảnh lên: $uri")

            val inputStream = context.contentResolver.openInputStream(uri)
            val byteArray = inputStream?.readBytes()
            inputStream?.close()

            if (byteArray == null) {
                Log.e("UPLOAD", "Không thể đọc dữ liệu ảnh")
                return@withContext Pair(null, null)
            }

            val requestBody = byteArray.toRequestBody("image/jpeg".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("image", "image.jpg", requestBody)

            Log.d("UPLOAD", "Gửi request đến server...")

            val response = apiService.uploadImage(body)

            // Xử lý kết quả trả về
            val responseBody = response
            Log.d("UPLOAD", "Nhận dữ liệu thành công!")

            // Chuyển danh sách bbox thành chuỗi để hiển thị
            /*val predictionsText = responseBody.predictions.joinToString("\n") {
                if (responseBody.predictions.isEmpty()) {
                    "Không có kết quả"
                } else {
                    if (it.`class` == 1) {
                        "U não"
                    } else {
                        "Không u não"
                    }
                }
                "Class: ${it.`class`}, Confidence: ${(it.confidence * 100).toInt()}%, BBox: ${it.bbox}"
            }*/
            val predictionsText = if (responseBody.predictions.isEmpty()) {
                "Không có kết quả"
            } else {
                responseBody.predictions.joinToString("\n") {
                    if (it.`class` == 1) "U não" else "Không u não"
                }.ifBlank { "Không có kết quả" } // Kiểm tra chuỗi rỗng
            }

            val texttxt = File(context.filesDir, "${timestamp}.txt")
            texttxt.writeText(predictionsText)

            // Giải mã ảnh từ hex
            val decodedBytes = responseBody.image.chunked(2)
                .map { it.toInt(16).toByte() }
                .toByteArray()

            // Lưu ảnh vào cache
            val file = File(context.filesDir, "${timestamp}.jpg")
            file.writeBytes(decodedBytes)

            return@withContext Pair(Uri.fromFile(file), predictionsText)
        } catch (e: Exception) {
            Log.e("UPLOAD", "Lỗi kết nối: ${e.message}", e)
            return@withContext Pair(null, null)
        }
    }
}



// 4. Sửa Composable
@Composable
fun UNaoScreen(navController: NavHostController) {
    val context = LocalContext.current
    val imageUri = remember { mutableStateOf<Uri?>(null) }

    val apiService = RetrofitClient.apiService
    val coroutineScope = rememberCoroutineScope()
    val resultText = remember { mutableStateOf("Chưa có kết quả") }
    val processedImageUri = remember { mutableStateOf<Uri?>(null) }

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            processedImageUri.value = null  // Xóa ảnh kết quả khi chọn ảnh mới
            resultText.value = "Chưa có kết quả"  // Reset kết quả dự đoán
            imageUri.value = uri
        }

    Scaffold(
        topBar = {
//            TopBarForAdd(navController)
//            TopBar(title = "Chuẩn đoán u não", navController = navController)
        }
    ) { innerPadding ->
        Image(
            painter = painterResource(id = R.drawable.nen_app),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentScale = ContentScale.Crop
        )
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .shadow(4.dp, RoundedCornerShape(12.dp)),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        imageUri.value?.let { uri ->
                            Image(
                                painter = rememberAsyncImagePainter(uri),
                                contentDescription = "Selected Image",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .size(200.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .border(
                                        2.dp,
                                        MaterialTheme.colorScheme.primary,
                                        RoundedCornerShape(12.dp)
                                    )
                            )
                        } ?: Text("Chọn ảnh để tải lên", style = MaterialTheme.typography.bodyLarge)

                        Spacer(modifier = Modifier.height(10.dp))
                        ElevatedButton(onClick = { launcher.launch("image/*") }) {
                            Text("Chọn ảnh")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                ElevatedButton(onClick = {
                    imageUri.value?.let { uri ->
                        coroutineScope.launch {
                            val (responseUri, predictions) = uploadImage(apiService, uri, context)
                            if (responseUri != null) {
                                processedImageUri.value = responseUri
                                resultText.value = predictions ?: "Không có kết quả"
                            } else {
                                Toast.makeText(context, "Lỗi khi tải ảnh", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } ?: Toast.makeText(context, "Vui lòng chọn ảnh trước", Toast.LENGTH_SHORT).show()
                }) {
                    Text("Tải lên")
                }

                Spacer(modifier = Modifier.height(10.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .shadow(4.dp, RoundedCornerShape(12.dp)),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        processedImageUri.value?.let { uri ->
                            Image(
                                painter = rememberAsyncImagePainter(uri),
                                contentDescription = "Processed Image",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .size(200.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .border(2.dp, Color.Green, RoundedCornerShape(12.dp))
                            )
                        } ?: Text("Chưa có ảnh xử lý", style = MaterialTheme.typography.bodyLarge)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = resultText.value,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(top = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    },
                    colors = IconButtonDefaults.iconButtonColors(Color.Black),
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
                IconButton(
                    onClick = {
                        navController.navigate("unaohistory")
                    },
                    colors = IconButtonDefaults.iconButtonColors(Color(0xFF2196F3)),
                ) {
                    Icon(
                        imageVector = Icons.Default.History,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
    }

}
