package com.example.healthtrackingapp.data.serverUNao

data class BoundingBox(
    val bbox: List<Int>,  // Danh sách tọa độ [x_min, y_min, x_max, y_max]
    val `class`: Int,     // Loại của phát hiện
    val confidence: Float // Độ tin cậy (0-1)
)

data class PredictionResponse(
    val predictions: List<BoundingBox>, // Cập nhật kiểu dữ liệu đúng
    val image: String // Chuỗi hex của ảnh
)