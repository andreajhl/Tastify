package com.example.library.utils

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

enum class SnackbarType {
    INFO,
    ERROR
}

object SnackbarManager {
    private val _messages = MutableSharedFlow<SnackbarMessage>()
    val messages = _messages.asSharedFlow()

    suspend fun showMessage(
        message: String,
        actionLabel: String? = null,
        onAction: (() -> Unit)? = null,
        type: SnackbarType = SnackbarType.INFO
    ) {
        _messages.emit(SnackbarMessage(message, actionLabel, onAction, type))
    }
}

data class SnackbarMessage(
    val message: String,
    val actionLabel: String? = null,
    val onAction: (() -> Unit)? = null,
    val type: SnackbarType
)