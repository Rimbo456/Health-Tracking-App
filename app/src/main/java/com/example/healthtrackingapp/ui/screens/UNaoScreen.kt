package com.example.healthtrackingapp.ui.screens

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

// 2. Tạo Retrofit client một cách tốt hơn (thêm vào file khác)
object RetrofitClient {
    private const val BASE_URL = "http://172.16.58.182:5000"

    val apiService: ApiServiceUNao by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServiceUNao::class.java)
    }
}

// 3. Sửa hàm uploadImage để sử dụng coroutines tốt hơn
suspend fun uploadImage(apiService: ApiServiceUNao, uri: Uri, context: Context): String? {
    return withContext(Dispatchers.IO) {
        try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val byteArray = inputStream?.readBytes()
            inputStream?.close()

            val requestBody = byteArray?.toRequestBody("image/jpeg".toMediaTypeOrNull())
            val body = requestBody?.let { MultipartBody.Part.createFormData("image", "image.jpg", it) }

            body?.let {
                try {
                    val response = apiService.uploadImage(it)
                    if (response.has("prediction")) {
                        response.get("prediction").asString
                    } else {
                        "Không tìm thấy kết quả dự đoán"
                    }
                } catch (e: Exception) {
                    "Lỗi server: ${e.message}"
                }
            }
        } catch (e: Exception) {
            Log.e("Upload", "Lỗi: ${e.message}", e)
            "Lỗi khi tải ảnh lên: ${e.message}"
        }
    }
}

// 4. Sửa Composable
@Composable
fun UNaoScreen() {
    val context = LocalContext.current
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri.value = uri
    }

    // Sử dụng Retrofit Client singleton
    val apiService = RetrofitClient.apiService
    val coroutineScope = rememberCoroutineScope()
    val resultText = remember { mutableStateOf("Chưa có kết quả") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        imageUri.value?.let { uri ->
            Image(
                painter = rememberAsyncImagePainter(uri),
                contentDescription = "Selected Image",
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(2.dp, Color.Gray, RoundedCornerShape(8.dp))
            )
        } ?: Text("Chọn ảnh để tải lên")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { launcher.launch("image/*") }) {
            Text("Chọn ảnh")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            imageUri.value?.let { uri ->
                coroutineScope.launch {
                    val response = uploadImage(apiService, uri, context)
                    resultText.value = response ?: "Lỗi khi tải ảnh"
                }
            } ?: Toast.makeText(context, "Vui lòng chọn ảnh trước", Toast.LENGTH_SHORT).show()
        }) {
            Text("Tải lên")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = resultText.value, fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }
}