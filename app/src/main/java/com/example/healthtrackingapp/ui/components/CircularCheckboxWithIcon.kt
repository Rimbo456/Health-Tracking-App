package com.example.healthtrackingapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

@Composable
fun CircularCheckboxWithIcon() {
    var isChecked by remember { mutableStateOf(false) }

    IconToggleButton(
        checked = isChecked,
        onCheckedChange = { isChecked = it }
    ) {
        Icon(
            imageVector = if (isChecked) Icons.Filled.CheckCircle else Icons.Outlined.RadioButtonUnchecked,
            contentDescription = "Checkbox",
            tint = if (isChecked) Color.Black else Color.Gray
        )
    }
}