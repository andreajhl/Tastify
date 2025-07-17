package com.example.authentication

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.login.LoginScreen
import com.example.navigation.Screen
import com.example.register.RegisterBottomSheet

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthenticationScreen(
    navController: NavHostController
) {
    var showRegister by remember { mutableStateOf(false) }
    var isLogged by remember { mutableStateOf(false) }

    fun setIsLogged() {
        isLogged = true
    }

    LaunchedEffect(isLogged) {
        if (isLogged == true) {
            navController.navigate(Screen.Home.route) {
                popUpTo(0) { inclusive = true }
                launchSingleTop = true
            }
        }
    }

    if (showRegister) {
        RegisterBottomSheet(
            onDismiss = { showRegister = false },
            onRegisterSuccess = { setIsLogged() }
        )
    }

    LoginScreen(
        onShowRegister = { showRegister = true },
        onLoginSuccess = { setIsLogged() },
    )
}

@Preview(showBackground = true)
@Composable
fun AuthenticationPreview() {
    val fakeNavController = rememberNavController()

    AuthenticationScreen(navController = fakeNavController)
}