package com.example.healthtrackingapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Calendar


fun getLastAndNext7Days(): List<LocalDate> {
    val today = LocalDate.now()

    // Lấy 7 ngày trước
    val pastDays = (1..7).map { today.minusDays(it.toLong()) }.reversed()

    // Ngày hiện tại
    val todayList = listOf(today)

    // Lấy 7 ngày sau
    val futureDays = (1..7).map { today.plusDays(it.toLong()) }

    // Ghép danh sách lại
    return pastDays + todayList + futureDays
}

@Composable
fun Calendar() {
    val days = getLastAndNext7Days()
    val selectedDay = remember { mutableStateOf<LocalDate?>(days[7]) }
    val today = days[7]
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            state = listState
        ) {
            items(days) { item ->
                Card(
                    onClick = {
                        selectedDay.value = item
                        scrollToCenter(listState, days.indexOf(item), coroutineScope)
                    },
                    modifier = Modifier.width(65.dp),
                    colors = if (item.dayOfMonth == selectedDay.value?.dayOfMonth) CardDefaults.cardColors(
                        Color.LightGray
                    ) else {
                        CardDefaults.cardColors(
                            Color.White
                        )
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .padding(vertical = 10.dp, horizontal = 10.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = when (item.dayOfWeek.toString()) {
                                "MONDAY" -> "TH 2"
                                "TUESDAY" -> "TH 3"
                                "WEDNESDAY" -> "TH 4"
                                "THURSDAY" -> "TH 5"
                                "FRIDAY" -> "TH 6"
                                "SATURDAY" -> "TH 7"
                                else -> "CN"
                            },
                            fontSize = 20.sp
                        )
                        Box(
                            modifier = if (selectedDay.value == item)
                                Modifier
                                    .clip(CircleShape)
                                    .background(Color.Black)
                                    .size(30.dp)
                            else Modifier
                                .clip(CircleShape)
                                .background(Color.White)
                                .size(30.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = item.dayOfMonth.toString(),
                                fontSize = 18.sp,
                                color = if (selectedDay.value == item) Color.White else Color.Black
                            )
                        }
                    }
                }
            }
        }
        Text(
            text = today.dayOfMonth.toString() + " thg " + today.monthValue.toString(),
            fontSize = 20.sp
        )
    }
    LaunchedEffect(key1 = Unit) {
        scrollToCenter(listState, 7, coroutineScope)
    }
}

private fun scrollToCenter(
    listState: LazyListState,
    index: Int,
    coroutineScope: CoroutineScope
) {
    coroutineScope.launch {
        // Sử dụng animateScrollToItem với offsetPx để đưa phần tử vào giữa
        listState.animateScrollToItem(
            index = index,
            scrollOffset = -listState.layoutInfo.viewportSize.width / 2 + listState.layoutInfo.visibleItemsInfo.firstOrNull()?.size?.div(
                2
            )!!
                ?: 0
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun CalendarPreview() {
    Calendar()
}