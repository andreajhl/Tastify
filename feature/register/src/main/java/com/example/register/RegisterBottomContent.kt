package com.example.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.common.InputField
import com.example.theme.ui.theme.AppAndroidTheme

@Composable
fun RegisterBottomContent(
    registerData: RegisterDataState,
    registerError: RegisterDataErrorState,
    isFormValid: Boolean,
    isLoading: Boolean,
    onRegister: () -> Unit,
    updateField: (String, String) -> Unit,
    validateField: (String) -> Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.register_title),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            InputField(
                label = stringResource(R.string.input_name),
                value = registerData.name,
                onValueChange = { updateField("name", it) },
                onBlur = { validateField("name") },
                error = if (registerError.name == true) stringResource(R.string.input_name_error) else ""
            )

            InputField(
                label = stringResource(R.string.input_last_name),
                value = registerData.lastName,
                onValueChange = { updateField("lastName", it) },
                onBlur = { validateField("lastName") },
                error = if (registerError.lastName == true) stringResource(R.string.input_last_name_error) else ""
            )

            InputField(
                label = stringResource(R.string.input_email),
                value = registerData.email,
                onValueChange = { updateField("email", it) },
                onBlur = { validateField("email") },
                error = if (registerError.email == true) stringResource(R.string.input_email_error) else ""
            )

            InputField(
                label = stringResource(R.string.input_password),
                value = registerData.password,
                onValueChange = { updateField("password", it) },
                onBlur = { validateField("password") },
                visualTransformation = PasswordVisualTransformation(),
                error = if (registerError.password == true) stringResource(R.string.input_password_error) else ""
            )

            InputField(
                label = stringResource(R.string.input_repeat_password),
                value = registerData.repeatPassword,
                onValueChange = { updateField("repeatPassword", it) },
                onBlur = { validateField("repeatPassword") },
                visualTransformation = PasswordVisualTransformation(),
                error = if (registerError.repeatPassword == true) stringResource(R.string.input_repeat_password_error) else ""
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onRegister,
            enabled = isFormValid && !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(
                text = stringResource(R.string.sign_up),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterBottomContentPreview() {
    val fakeRegisterData = RegisterDataState(
        name = "John",
        lastName = "Doe",
        email = "john.doe@example.com",
        password = "password123",
        repeatPassword = "password123"
    )

    val fakeRegisterError = RegisterDataErrorState(
        name = false,
        lastName = false,
        email = false,
        password = false,
        repeatPassword = false
    )

    AppAndroidTheme(darkTheme = false, dynamicColor = false) {
        RegisterBottomContent(
            registerData = fakeRegisterData,
            registerError = fakeRegisterError,
            isFormValid = true,
            isLoading = false,
            onRegister = {},
            updateField = { _, _ -> },
            validateField = { true }
        )
    }
}