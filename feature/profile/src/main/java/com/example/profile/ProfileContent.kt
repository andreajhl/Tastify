package com.example.profile

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.example.model.Profile
import com.example.theme.AppAndroidTheme
import com.example.theme.DefaultScreenPadding

@Composable
fun ProfileContent(
    padding: PaddingValues,
    profileData: Profile,
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
                model = profileData.profileImageUri,
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
            value = profileData.name,
            onValueChange = { onUpdateField("name", it) },
            onBlur = {},
            error = "",
            enabled = isEditing
        )

        InputField(
            label = stringResource(R.string.input_last_name),
            value = profileData.lastName,
            onValueChange = { onUpdateField("lastName", it) },
            onBlur = {},
            error = "",
            enabled = isEditing
        )

        InputField(
            label = stringResource(R.string.input_adress),
            value = profileData.address,
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
                value = profileData.streetNumber,
                onValueChange = { onUpdateField("streetNumber", it) },
                onBlur = {},
                error = "",
                enabled = isEditing,
                modifier = Modifier.weight(1f)
            )

            InputField(
                label = stringResource(R.string.input_floor),
                value = profileData.floor,
                onValueChange = { onUpdateField("floor", it) },
                onBlur = {},
                error = "",
                enabled = isEditing,
                modifier = Modifier.weight(1f)
            )

            InputField(
                label = stringResource(R.string.input_apartment),
                value = profileData.apartment,
                onValueChange = { onUpdateField("apartment", it) },
                onBlur = {},
                error = "",
                enabled = isEditing,
                modifier = Modifier.weight(1f)
            )
        }

        InputField(
            label = stringResource(R.string.input_phone),
            value = profileData.phone,
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
    val fakeProfileState = Profile(
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
            padding = PaddingValues(0.dp), profileData = fakeProfileState,
            isEditing = true,
            onUpdateField = { _, _ -> },
            onImageSelected = {}
        )
    }
}