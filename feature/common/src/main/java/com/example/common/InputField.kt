package com.example.common

import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.theme.ui.theme.MainColor

@Composable
fun InputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    onBlur: () -> Unit,
    error: String,
    enabled: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    modifier: Modifier = Modifier
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
        enabled = enabled,
        visualTransformation = visualTransformation,
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                if (focusState.isFocused) {
                    wasFocused = true
                } else if (wasFocused) {
                    onBlur()
                    showError = error.isNotEmpty()
                }
            },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MainColor,
            errorBorderColor = Color.Red
        )
    )

    if (showError) {
        Text(
            text = error,
            color = Color.Red,
            style = MaterialTheme.typography.bodySmall
        )
    }
}