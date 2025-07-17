package com.example.profile

import androidx.compose.material3.AlertDialog
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun CameraOrGalleryDialog(
    onDismiss: () -> Unit,
    onCameraSelected: () -> Unit,
    onGallerySelected: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Seleccionar opción") },
        text = {
            Column {
                TextButton(onClick = {
                    onCameraSelected()
                    onDismiss()
                }) {
                    Text("Abrir cámara")
                }
                TextButton(onClick = {
                    onGallerySelected()
                    onDismiss()
                }) {
                    Text("Elegir desde galería")
                }
            }
        },
        confirmButton = {},
        dismissButton = {}
    )
}