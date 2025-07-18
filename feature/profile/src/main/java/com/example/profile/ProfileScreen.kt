package com.example.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(onOpenDrawer: () -> Unit) {
    val profileViewModel: ProfileViewModel = hiltViewModel()
    val profileState by profileViewModel.profile.collectAsState()
    val isEditing = profileViewModel.isEditing

    LaunchedEffect(Unit) {
        profileViewModel.loadProfile()
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
            profileState = profileState,
            isEditing = isEditing,
            onUpdateField = { key, value -> profileViewModel.updateLoginField(key, value) },
            onImageSelected = { uri -> profileViewModel.updateProfilePicture(uri) }
        )
    }
}
