package com.example.profile

import androidx.compose.material3.AlertDialog
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun CameraOrGalleryDialog(
    onDismiss: () -> Unit,
    onCameraSelected: () -> Unit,
    onGallerySelected: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.camera_title)) },
        text = {
            Column {
                TextButton(onClick = {
                    onCameraSelected()
                    onDismiss()
                }) {
                    Text(stringResource(R.string.open_camera))
                }
                TextButton(onClick = {
                    onGallerySelected()
                    onDismiss()
                }) {
                    Text(stringResource(R.string.open_galery))
                }
            }
        },
        confirmButton = {},
        dismissButton = {}
    )
}