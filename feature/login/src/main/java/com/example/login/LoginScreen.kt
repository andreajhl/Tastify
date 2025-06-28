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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.common.InputField
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun LoginScreen(
    loginViewModel: LoginContract,
    onLoginSuccess: () -> Unit,
    onShowRegister: () -> Unit,
    isLogged: Boolean
) {
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
            label = "Email",
            value = loginState.email,
            onValueChange = { loginViewModel.updateLoginField("email", it) },
            onBlur = { loginViewModel.validateEmail() },
            error = errorState.email
        )

        Spacer(modifier = Modifier.height(16.dp))

        InputField(
            label = "Password",
            value = loginState.password,
            onValueChange = { loginViewModel.updateLoginField("password", it) },
            onBlur = { loginViewModel.validatePassword() },
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

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    class FakeLoginViewModel : LoginContract {
        private val _login = MutableStateFlow(LoginState(email = "test@example.com", password = "123456"))
        private val _errorMsg = MutableStateFlow(LoginErrorState())

        override val login: StateFlow<LoginState> get() = _login
        override val errorMsg: StateFlow<LoginErrorState> get() = _errorMsg

        override fun updateLoginField(key: String, value: String) {
            _login.value = when (key) {
                "email" -> _login.value.copy(email = value)
                "password" -> _login.value.copy(password = value)
                else -> _login.value
            }
        }

        override fun validateEmail(): Boolean = true
        override fun validatePassword(): Boolean = true
        override fun isValidateData(): Boolean = true
    }

    LoginScreen(
        loginViewModel = FakeLoginViewModel(),
        onLoginSuccess = {},
        onShowRegister = {},
        isLogged = false
    )
}