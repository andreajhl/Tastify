package com.example.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.common.InputField

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(onOpenDrawer: () -> Unit) {
    val profileViewModel: ProfileViewModel = hiltViewModel()
    val profileState by profileViewModel.profile.collectAsState()

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
                            imageVector = if (profileViewModel.isEditing) Icons.Default.Check else Icons.Default.Edit,
                            contentDescription = if (profileViewModel.isEditing) stringResource(R.string.save) else stringResource(R.string.edit)
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .size(100.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                AsyncImage(
                    model = "https://i.imgur.com/GF7vZtT.png",
                    contentDescription = stringResource(R.string.profile_picture),
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                IconButton(
                    onClick = {  },
                    modifier = Modifier
                        .size(28.dp)
                        .background(Color.DarkGray.copy(alpha = 0.7f), shape = CircleShape)
                        .padding(4.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.camera_icon),
                        contentDescription = stringResource(R.string.edit_picture),
                        modifier = Modifier.size(16.dp),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }
            }

            InputField(
                label = stringResource(R.string.input_name),
                value = profileState.name,
                onValueChange = { profileViewModel.updateLoginField("name", it) },
                onBlur = {},
                error = "",
                enabled = profileViewModel.isEditing
            )

            InputField(
                label = stringResource(R.string.input_last_name),
                value = profileState.lastName,
                onValueChange = { profileViewModel.updateLoginField("lastName", it) },
                onBlur = {},
                error = "",
                enabled = profileViewModel.isEditing
            )

            InputField(
                label = stringResource(R.string.input_adress),
                value = profileState.address,
                onValueChange = { profileViewModel.updateLoginField("address", it) },
                onBlur = {},
                error = "",
                enabled = profileViewModel.isEditing
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                InputField(
                    label = stringResource(R.string.input_height),
                    value = profileState.streetNumber,
                    onValueChange = { profileViewModel.updateLoginField("streetNumber", it) },
                    onBlur = {},
                    error = "",
                    enabled = profileViewModel.isEditing,
                    modifier = Modifier.weight(1f)
                )

                InputField(
                    label = stringResource(R.string.input_apartment),
                    value = profileState.apartment,
                    onValueChange = { profileViewModel.updateLoginField("apartment", it) },
                    onBlur = {},
                    error = "",
                    enabled = profileViewModel.isEditing,
                    modifier = Modifier.weight(1f)
                )

                InputField(
                    label = stringResource(R.string.input_floor),
                    value = profileState.floor,
                    onValueChange = { profileViewModel.updateLoginField("floor", it) },
                    onBlur = {},
                    error = "",
                    enabled = profileViewModel.isEditing,
                    modifier = Modifier.weight(1f)
                )
            }

            InputField(
                label = stringResource(R.string.input_phone),
                value = profileState.phone,
                onValueChange = { profileViewModel.updateLoginField("phone", it) },
                onBlur = {},
                error = "",
                enabled = profileViewModel.isEditing
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    Profile(onOpenDrawer = {})
}