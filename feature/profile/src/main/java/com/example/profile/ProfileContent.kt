package com.example.profile

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.common.InputField
import com.example.theme.ui.theme.AppAndroidTheme
import com.example.theme.ui.theme.DefaultScreenPadding

@Composable
fun ProfileContent(
    padding: PaddingValues,
    profileState: ProfileState,
    isEditing: Boolean,
    onUpdateField: (String, String) -> Unit,
    onImageSelected: (Uri) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(padding)
            .padding(DefaultScreenPadding)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier.size(100.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            AsyncImage(
                model = profileState.profileImageUri.ifEmpty { "https://i.imgur.com/GF7vZtT.png" },
                contentDescription = stringResource(R.string.profile_picture),
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            ProfileImageButton(
                onImageSelected = onImageSelected
            )
        }

        InputField(
            label = stringResource(R.string.input_name),
            value = profileState.name,
            onValueChange = { onUpdateField("name", it) },
            onBlur = {},
            error = "",
            enabled = isEditing
        )

        InputField(
            label = stringResource(R.string.input_last_name),
            value = profileState.lastName,
            onValueChange = { onUpdateField("lastName", it) },
            onBlur = {},
            error = "",
            enabled = isEditing
        )

        InputField(
            label = stringResource(R.string.input_adress),
            value = profileState.address,
            onValueChange = { onUpdateField("address", it) },
            onBlur = {},
            error = "",
            enabled = isEditing
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            InputField(
                label = stringResource(R.string.input_height),
                value = profileState.streetNumber,
                onValueChange = { onUpdateField("streetNumber", it) },
                onBlur = {},
                error = "",
                enabled = isEditing,
                modifier = Modifier.weight(1f)
            )

            InputField(
                label = stringResource(R.string.input_floor),
                value = profileState.floor,
                onValueChange = { onUpdateField("floor", it) },
                onBlur = {},
                error = "",
                enabled = isEditing,
                modifier = Modifier.weight(1f)
            )

            InputField(
                label = stringResource(R.string.input_apartment),
                value = profileState.apartment,
                onValueChange = { onUpdateField("apartment", it) },
                onBlur = {},
                error = "",
                enabled = isEditing,
                modifier = Modifier.weight(1f)
            )
        }

        InputField(
            label = stringResource(R.string.input_phone),
            value = profileState.phone,
            onValueChange = { onUpdateField("phone", it) },
            onBlur = {},
            error = "",
            enabled = isEditing
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileContentPreview() {
    val fakeProfileState = ProfileState(
        profileImageUri = "",
        name = "Andrea",
        lastName = "Hernandez",
        address = "123 Main St",
        streetNumber = "456",
        floor = "2",
        apartment = "B",
        phone = "+54 11 1234-5678"
    )

    AppAndroidTheme(darkTheme = true, dynamicColor = false) {
        ProfileContent(
            padding = PaddingValues(0.dp),
            profileState = fakeProfileState,
            isEditing = true,
            onUpdateField = { _, _ -> },
            onImageSelected = {}
        )
    }
}