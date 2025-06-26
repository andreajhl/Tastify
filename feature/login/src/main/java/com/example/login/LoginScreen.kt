package com.example.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.common.InputField

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit,
    onShowRegister: () -> Unit,
    isLogged: Boolean
) {
    val loginState by viewModel.login.collectAsState()
    val errorState by viewModel.errorMsg.collectAsState()

    LaunchedEffect (isLogged) {
        if (isLogged) onLoginSuccess()
    }

    val isFormValid = viewModel.isValidateData()

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        InputField(
            label = "Email",
            value = loginState.email,
            onValueChange = { viewModel.updateLoginField("email", it) },
            onBlur = { viewModel.validateEmail() },
            error = errorState.email
        )

        Spacer(modifier = Modifier.height(16.dp))

        InputField(
            label = "Password",
            value = loginState.password,
            onValueChange = { viewModel.updateLoginField("password", it) },
            onBlur = { viewModel.validatePassword() },
            error = errorState.password,
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button (
            onClick = { onLoginSuccess() },
            enabled = isFormValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar sesión")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton (
            onClick = onShowRegister,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("¿No tenés cuenta? Registrate")
        }
    }
}