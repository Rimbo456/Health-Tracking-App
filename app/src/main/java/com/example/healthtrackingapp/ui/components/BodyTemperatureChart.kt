package com.example.healthtrackingapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.VelocityTrackerCompat.clear
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.compose.common.vicoTheme
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer
import kotlinx.coroutines.runBlocking
import java.time.LocalDate

@Composable
private fun JetpackComposeBasicLineChart(
    modelProducer: CartesianChartModelProducer,
    modifier: Modifier = Modifier,
) {

    CartesianChartHost(
        chart =
            rememberCartesianChart(
                rememberLineCartesianLayer(
                    lineProvider = LineCartesianLayer.LineProvider.series(
                        vicoTheme.lineCartesianLayerColors.map { color ->
                            LineCartesianLayer.rememberLine(
                                LineCartesianLayer.LineFill.single(
                                    fill(
                                        Color.Red
                                    )
                                )
                            )
                        }
                    ),
                ),
                rememberLineCartesianLayer(
                    lineProvider = LineCartesianLayer.LineProvider.series(
                        vicoTheme.lineCartesianLayerColors.map { color ->
                            LineCartesianLayer.rememberLine(
                                LineCartesianLayer.LineFill.single(
                                    fill(
                                        Color.Transparent
                                    )
                                )
                            )
                        }
                    ),
                ),
                startAxis = VerticalAxis.rememberStart(
                    valueFormatter = remember {
                        CartesianValueFormatter { context, value, verticalAxisPosition ->
                            "${value.toInt()} °C"
                        }
                    }),
                bottomAxis = HorizontalAxis.rememberBottom(
                    valueFormatter = remember {
                        CartesianValueFormatter { context, value, verticalAxisPosition ->
                            "${value.toInt()} :00"
                        }
                    }),
            ),
        modelProducer = modelProducer,
        modifier = modifier,
    )
}

@Composable
fun BodyTemperatureChart(modifier: Modifier = Modifier,selectedDate: LocalDate) {
    val modelProducer = remember { CartesianChartModelProducer() }
    val db = FirebaseFirestore.getInstance()
    var user by remember { mutableStateOf(Firebase.auth.currentUser) }
    var dataPointss = remember { mutableStateListOf<Int>() }
    LaunchedEffect(selectedDate) {
        // Tạo Date object cho đầu ngày và cuối ngày
        val calendar = java.util.Calendar.getInstance()
        calendar.set(
            selectedDate.year,
            selectedDate.monthValue - 1,
            selectedDate.dayOfMonth,
            0, 0, 0
        )
        val startDate = calendar.time

        calendar.set(
            selectedDate.year,
            selectedDate.monthValue - 1,
            selectedDate.dayOfMonth,
            23, 59, 59
        )
        val endDate = calendar.time

        // Chuyển đổi Date thành Timestamp của Firestore
        val startTimestamp = Timestamp(startDate)
        val endTimestamp = Timestamp(endDate)
        db.collection("users")
            .document(user!!.uid)
            .collection("temperature")
            .whereGreaterThanOrEqualTo("timestamp", startTimestamp)
            .whereLessThanOrEqualTo("timestamp", endTimestamp)
            .get()
            .addOnSuccessListener { documents ->
                dataPointss.clear()
                for (document in documents) {
                    document.getString("nhietdo")?.let { dataPointss.add(it.toInt()) }
                }
            }
            .addOnFailureListener { exception ->
                println("Error getting documents: $exception")
            }
    }
    LaunchedEffect(dataPointss.toList()) {
        modelProducer.runTransaction {
            // Learn more: https://patrykandpatrick.com/vmml6t.
            if (dataPointss.isNotEmpty()) { // Kiểm tra danh sách không rỗng trước khi vẽ
                lineSeries { series(dataPointss.map { it.toInt() }) }
            } else {
                lineSeries { series(0) }
            }
            lineSeries { series(53) }
        }
    }
    PreviewBox { JetpackComposeBasicLineChart(modelProducer, modifier) }
}

@Composable
@Preview
private fun Preview() {
    val modelProducer = remember { CartesianChartModelProducer() }
    // Use `runBlocking` only for previews, which don’t support asynchronous execution.
    runBlocking {
        modelProducer.runTransaction {
            // Learn more: https://patrykandpatrick.com/vmml6t.
            lineSeries { series(13, 8, 7, 12, 0, 1, 15, 14, 0, 11, 6, 12, 0, 11, 12, 11) }
            lineSeries { series(10, 4, 8, 2, 3, 7, 9, 10, 12, 11, 6, 12, 0, 11, 12, 11) }
        }
    }
    PreviewBox { JetpackComposeBasicLineChart(modelProducer) }
}

@Composable
private fun PreviewBox(content: @Composable BoxScope.() -> Unit) {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp), content = content
    )
}

