package com.example.login

import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.common.InputField
import com.example.theme.ui.theme.MainColor
import androidx.compose.ui.graphics.Path
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.theme.ui.theme.DefaultScreenPadding

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
