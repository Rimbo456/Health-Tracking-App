package com.example.healthtrackingapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Date

@Composable
fun ItemGoal(
    content: String,
    title: String,
    date: String,
    isChecked: Boolean,
    modifier: Modifier
) {
    Card(
        onClick = { /*TODO*/ },
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement =  Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier.padding(15.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 25.sp,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Text(
                    text = content,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Text(
                    text = date,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            }
//            CircularCheckboxWithIcon()
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ItemGoalPreview() {

}