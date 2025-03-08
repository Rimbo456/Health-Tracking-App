package com.example.healthtrackingapp.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun PieChart(
    data: List<Float>, // Danh sách giá trị
    colors: List<Color>, // Màu sắc cho các phần
    modifier: Modifier = Modifier
) {
    val total = data.sum()
    val angles = data.map { it / total * 360f } // Tính góc từng phần

    Canvas(modifier = modifier.size(200.dp)) {
        var startAngle = 0f
        angles.forEachIndexed { index, angle ->
            drawArc(
                color = colors[index],
                startAngle = startAngle,
                sweepAngle = angle,
                useCenter = true
            )
            startAngle += angle
        }
    }
}

@Composable
fun PieChartScreen() {
    val data = listOf(30f, 20f, 50f) // Dữ liệu: 3 phần
    val colors = listOf(Color.Red, Color.Blue, Color.Green) // Màu tương ứng

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        PieChart(data, colors)
    }
}

@Preview(showBackground = true)
@Composable
fun PieChartPreview() {
    PieChartScreen()
}