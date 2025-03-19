package com.example.healthtrackingapp.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashBoardViewModel : ViewModel() {
    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()

    private val _textToShow = MutableStateFlow("")
    val textToShow: StateFlow<String> = _textToShow.asStateFlow()

    private val fullText = "Hôm nay bạn cảm thấy thế nào?"

    init {
        startTypingEffect()
    }

    fun toggleDialog() {
        _showDialog.value = !_showDialog.value
    }

    private fun startTypingEffect() {
        CoroutineScope(Dispatchers.Main).launch {
            fullText.forEachIndexed { index, _ ->
                _textToShow.update { fullText.substring(0, index + 1) }
                delay(50)
            }
        }
    }
}