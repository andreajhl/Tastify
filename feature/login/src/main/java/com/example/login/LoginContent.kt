package com.example.login

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.common.InputField
import com.example.theme.AppAndroidTheme
import com.example.theme.DefaultScreenPadding

@Composable
fun LoginContent(
    loginData: LoginData,
    errorState: LoginErrorState,
    isFormValid: Boolean,
    isLoading: Boolean,
    onLogin: () -> Unit,
    onShowRegister: () -> Unit,
    updateLoginField: (String, String) -> Unit,
    validateField: (String) -> Boolean
) {
    val primaryColor = MaterialTheme.colorScheme.primary

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Top
    ) {
        Box(contentAlignment = Alignment.Center) {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(330.dp)
            ) {
                val path = Path().apply {
                    moveTo(0f, size.height)

                    quadraticBezierTo(
                        size.width * 0.2f, size.height * 0.75f,
                        size.width * 0.55f, size.height * 0.95f
                    )

                    quadraticBezierTo(
                        size.width * 0.9f, size.height * 1.13f,
                        size.width * 1.2f, size.height * 0.65f
                    )

                    lineTo(size.width, 0f)
                    lineTo(0f, 0f)
                    close()
                }

                drawPath(
                    path = path,
                    color = primaryColor
                )
            }
            Image(
                painter = painterResource(id = R.drawable.brand),
                contentDescription = "Tastify logo",
                modifier = Modifier.size(270.dp)
            )
        }

        Column(modifier = Modifier.padding(DefaultScreenPadding)) {
            InputField(
                label = stringResource(R.string.email_label),
                value = loginData.email,
                onValueChange = { updateLoginField("email", it) },
                onBlur = { validateField("email") },
                error = errorState.email
            )

            Spacer(modifier = Modifier.height(16.dp))

            InputField(
                label = stringResource(R.string.password_label),
                value = loginData.password,
                onValueChange = { updateLoginField("password", it) },
                onBlur = { validateField("password") },
                error = errorState.password,
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onLogin,
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
                    text = stringResource(R.string.sign_in_label),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            TextButton(
                onClick = onShowRegister,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(stringResource(R.string.not_found_account_label))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginContentPreview() {
    val fakeLoginData = LoginData(
        email = "john.doe@example.com",
        password = "password123"
    )

    val fakeErrorState = LoginErrorState(
        email = "",
        password = ""
    )

    AppAndroidTheme(darkTheme = false, dynamicColor = false) {
        LoginContent(
            loginData = fakeLoginData,
            errorState = fakeErrorState,
            isFormValid = true,
            isLoading = false,
            onLogin = {},
            onShowRegister = {},
            updateLoginField = { _, _ -> },
            validateField = { true }
        )
    }
}