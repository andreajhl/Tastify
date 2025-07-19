package com.example.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.theme.AppAndroidTheme

@Composable
fun ToggleButton(
    isActive: Boolean,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.38f)
        ),
        shape = RoundedCornerShape(30.dp)
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun ToggleButtonPreview() {
    AppAndroidTheme(darkTheme = true, dynamicColor = false) {
        Column {
            ToggleButton(
                isActive = false,
                onClick = {},
            ) {
                Text("Test 1")
            }
            ToggleButton(
                isActive = true,
                onClick = {},
            ) {
                Text("Test 2")
            }
        }
    }
}