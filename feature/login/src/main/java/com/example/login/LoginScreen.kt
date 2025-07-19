package com.example.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LoginScreen(
    onShowRegister: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    val loginViewModel: LoginViewModel = hiltViewModel()

    val errorState by loginViewModel.errorMsg.collectAsState()
    val loginData by loginViewModel.loginData.collectAsState()
    val loginState by loginViewModel.loginState.collectAsState()

    val isFormValid by remember(loginData, errorState) {
        derivedStateOf { loginViewModel.isValidateData() }
    }

    fun validateFieldByKey(key: String): Boolean {
        return when (key) {
            "email" -> loginViewModel.validateEmail()
            "password" -> loginViewModel.validatePassword()
            else -> false
        }
    }

    LaunchedEffect(loginState.isSuccess) {
        if (loginState.isSuccess == true) {
            onLoginSuccess()
        }
    }

    LoginContent(
        loginData = loginData,
        errorState = errorState,
        isFormValid = isFormValid,
        isLoading = loginState.isLoading,
        onLogin = { loginViewModel.executeLogin() },
        onShowRegister = onShowRegister,
        updateLoginField = { key, value -> loginViewModel.updateLoginField(key, value) },
        validateField = { key -> validateFieldByKey(key) }
    )
}
