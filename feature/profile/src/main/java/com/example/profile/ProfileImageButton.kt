package com.example.profile

import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File

@Composable
fun ProfileImageButton(
    onImageSelected: (Uri) -> Unit
) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var tempUri by remember { mutableStateOf<Uri?>(null) }

    fun createTempImageUri(context: Context): Uri {
        val image = File(context.cacheDir, "${System.currentTimeMillis()}.jpg")
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            image
        )
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && tempUri != null) {
            onImageSelected(tempUri!!)
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { onImageSelected(it) }
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            val uri = createTempImageUri(context)
            tempUri = uri
            cameraLauncher.launch(uri)
        }
    }

    val backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
    val iconTintColor = MaterialTheme.colorScheme.onSurface

    IconButton(
        onClick = { showDialog = true },
        modifier = Modifier
            .offset(x = 10.dp, y = 10.dp)
            .background(backgroundColor, shape = CircleShape)
    ) {
        Icon(
            imageVector = Icons.Default.AddAPhoto,
            contentDescription = "Edit picture",
            modifier = Modifier.size(25.dp),
            tint = iconTintColor
        )
    }

    if (showDialog) {
        CameraOrGalleryDialog(
            onDismiss = { showDialog = false },
            onCameraSelected = {
                if (ContextCompat.checkSelfPermission(
                        context,
                        android.Manifest.permission.CAMERA
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    val uri = createTempImageUri(context)
                    tempUri = uri
                    cameraLauncher.launch(uri)
                } else {
                    cameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
                }
            },
            onGallerySelected = {
                galleryLauncher.launch("image/*")
            }
        )
    }
}