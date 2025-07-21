package com.example.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.library.utils.SnackbarType
import com.example.theme.AppAndroidTheme

data class CustomSnackbarVisuals(
    val type: SnackbarType,
    override val actionLabel: String?,
    override val duration: SnackbarDuration,
    override val message: String,
    override val withDismissAction: Boolean,
) : SnackbarVisuals

@Composable
fun CustomSnackbar(
    hostState: SnackbarHostState
) {
    SnackbarHost(
        hostState = hostState,
        snackbar = { snackbarData ->
            val customVisuals = snackbarData.visuals as? CustomSnackbarVisuals
            val type = customVisuals?.type ?: SnackbarType.INFO

            val (backgroundColor, contentColor, icon) = when (type) {
                SnackbarType.ERROR -> Triple(
                    MaterialTheme.colorScheme.errorContainer,
                    MaterialTheme.colorScheme.onErrorContainer,
                    Icons.Default.Error
                )

                SnackbarType.INFO -> Triple(
                    MaterialTheme.colorScheme.primaryContainer,
                    MaterialTheme.colorScheme.onPrimaryContainer,
                    Icons.Default.Info
                )
            }

            Snackbar(
                shape = RoundedCornerShape(12.dp),
                containerColor = backgroundColor,
                contentColor = contentColor,
                action = {
                    snackbarData.visuals.actionLabel?.let { actionLabel ->
                        TextButton(onClick = { snackbarData.performAction() }) {
                            Text(
                                text = actionLabel.uppercase(),
                                style = MaterialTheme.typography.labelLarge,
                                color = contentColor
                            )
                        }
                    }
                }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = contentColor,
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = snackbarData.visuals.message,
                        style = MaterialTheme.typography.bodyMedium,
                        color = contentColor
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun CustomSnackbarErrorPreview() {
    val visuals = object : SnackbarVisuals {
        override val actionLabel: String? = "retry"
        override val duration: SnackbarDuration = SnackbarDuration.Short
        override val message: String = "This is an error snackbar."
        override val withDismissAction: Boolean = true
    }

    val backgroundColorError = MaterialTheme.colorScheme.errorContainer
    val contentColorError = MaterialTheme.colorScheme.onErrorContainer

    val backgroundColorInfo = MaterialTheme.colorScheme.primaryContainer
    val contentColorInfo = MaterialTheme.colorScheme.onPrimaryContainer

    AppAndroidTheme(darkTheme = false, dynamicColor = false) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // ERROR Snackbar
            Snackbar(
                shape = RoundedCornerShape(12.dp),
                containerColor = backgroundColorError,
                contentColor = contentColorError,
                action = {
                    visuals.actionLabel?.let { actionLabel ->
                        TextButton(onClick = { /* no-op */ }) {
                            Text(
                                text = actionLabel.uppercase(),
                                style = MaterialTheme.typography.labelLarge,
                                color = contentColorError
                            )
                        }
                    }
                }
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Error,
                        contentDescription = null,
                        tint = contentColorError,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = visuals.message,
                        style = MaterialTheme.typography.bodyMedium,
                        color = contentColorError
                    )
                }
            }

            Snackbar(
                shape = RoundedCornerShape(12.dp),
                containerColor = backgroundColorInfo,
                contentColor = contentColorInfo,
                action = {
                    TextButton(onClick = { /* no-op */ }) {
                        Text(
                            text = "UNDO",
                            style = MaterialTheme.typography.labelLarge,
                            color = contentColorInfo
                        )
                    }
                }
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = contentColorInfo,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "This is an info snackbar.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = contentColorInfo
                    )
                }
            }
        }
    }
}