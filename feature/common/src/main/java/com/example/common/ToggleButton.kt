package com.example.common

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.theme.ui.theme.MainColor

@Composable
fun ToggleButton(
    isActive: Boolean,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isActive) MainColor else Color.LightGray
        ),
        shape = RoundedCornerShape(30.dp)
    ) {
        content()
    }
}