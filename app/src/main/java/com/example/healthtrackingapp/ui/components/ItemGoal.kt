package com.example.healthtrackingapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ItemGoal(
    content: String,
    isChecked: Boolean,
    modifier: Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement =  Arrangement.SpaceBetween,
        modifier = modifier,
    ) {
        Text(text = content)
        CircularCheckboxWithIcon()
    }
}

@Preview(showBackground = true)
@Composable
fun ItemGoalPreview() {
}