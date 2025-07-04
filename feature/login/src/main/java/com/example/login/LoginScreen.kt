package com.example.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap.Companion.Square
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.common.InputField
import com.example.theme.ui.theme.MainColor

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onShowRegister: () -> Unit,
    isLogged: Boolean
) {
    val loginViewModel: LoginViewModel = hiltViewModel()

    val loginState by loginViewModel.login.collectAsState()
    val errorState by loginViewModel.errorMsg.collectAsState()

    LaunchedEffect (isLogged) {
        if (isLogged) onLoginSuccess()
    }

    val isFormValid = loginViewModel.isValidateData()

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        InputField(
            label = stringResource(R.string.email_label),
            value = loginState.email,
            onValueChange = { loginViewModel.updateLoginField("email", it) },
            onBlur = { loginViewModel.validateEmail() },
            error = errorState.email
        )

        Spacer(modifier = Modifier.height(16.dp))

        InputField(
            label = stringResource(R.string.password_label),
            value = loginState.password,
            onValueChange = { loginViewModel.updateLoginField("password", it) },
            onBlur = { loginViewModel.validatePassword() },
            error = errorState.password,
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onLoginSuccess() },
            enabled = isFormValid,
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MainColor)
        ) {
            Text(stringResource(
                R.string.sign_in_label),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        TextButton(
            onClick = onShowRegister,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.textButtonColors(
                contentColor = MainColor
            )
        ) {
            Text(stringResource(R.string.not_found_account_label))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        onLoginSuccess = {},
        onShowRegister = {},
        isLogged = false
    )
}