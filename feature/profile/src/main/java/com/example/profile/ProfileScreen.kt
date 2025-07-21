package com.example.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.library.utils.SnackbarManager

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(onOpenDrawer: () -> Unit) {
    val context = LocalContext.current

    val profileViewModel: ProfileViewModel = hiltViewModel()

    val profileData by profileViewModel.profileData.collectAsState()
    val profileState by profileViewModel.profileState.collectAsState()

    val isEditing = profileViewModel.isEditing

    LaunchedEffect(Unit) {
        profileViewModel.loadProfile()
    }

    LaunchedEffect(profileState) {
        if (profileState.isError == true) {
            SnackbarManager.showMessage(
                actionLabel = context.getString(R.string.profile_update_failed_action),
                message = context.getString(R.string.profile_update_failed),
                onAction = { profileViewModel.saveProfileChanges() }
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.profile_title),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onOpenDrawer) {
                        Icon(Icons.Default.Menu, contentDescription = stringResource(R.string.profile_menu))
                    }
                },
                actions = {
                    IconButton(onClick = { profileViewModel.toggleEditMode() }) {
                        Icon(
                            imageVector = if (isEditing) Icons.Default.Check else Icons.Default.Edit,
                            contentDescription = if (isEditing) stringResource(R.string.save) else stringResource(R.string.edit)
                        )
                    }
                }
            )
        }
    ) { padding ->
        ProfileContent(
            padding = padding,
            profileData = profileData,
            isEditing = isEditing,
            onUpdateField = { key, value -> profileViewModel.updateLoginField(key, value) },
            onImageSelected = { uri -> profileViewModel.updateProfilePicture(uri) }
        )
    }
}
