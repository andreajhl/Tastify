package com.example.common

import android.util.Log
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.theme.ui.theme.MainColor

@Composable
fun InputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    onBlur: () -> Unit = {},
    error: String = "",
    enabled: Boolean = true,
    pattern: ((String) -> String)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    var showError by remember { mutableStateOf(false) }

    fun onChange(input: String) {
        val formatted = pattern?.invoke(input) ?: input
        onValueChange(formatted)
    }

    fun onFocus(focusState: FocusState) {
        if (focusState.isFocused) {
            showError = false
        } else {
            onBlur()
            showError = error.isNotEmpty()
        }
    }

    LaunchedEffect(error) {
        if (error.isEmpty()) {
            showError = false
        }
    }

    Column(
        modifier = modifier.wrapContentWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = { onChange(it) },
            label = { Text(label) },
            isError = showError,
            enabled = enabled,
            visualTransformation = visualTransformation,
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { onFocus(it) },
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
}