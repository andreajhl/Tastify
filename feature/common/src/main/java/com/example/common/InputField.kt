package com.example.common

import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color

@Composable
fun InputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    onBlur: () -> Unit,
    error: String,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    var wasFocused by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = {
            onValueChange(it)
            if (wasFocused) showError = error.isNotEmpty()
        },
        label = { Text(label) },
        isError = showError,
        visualTransformation = visualTransformation,
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                if (focusState.isFocused) {
                    wasFocused = true
                } else if (wasFocused) {
                    onBlur()
                    showError = error.isNotEmpty()
                }
            }
    )

    if (showError) {
        Text(
            text = error,
            color = Color.Red,
            style = MaterialTheme.typography.bodySmall
        )
    }
}