package com.example.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.login.LoginScreen
import com.example.login.LoginViewModel
import com.example.orchestrator.Orchestrator
import com.example.register.RegisterBottomSheet
import com.example.register.RegisterViewModel
import com.example.session.SessionManager
import com.example.theme.ui.theme.AppAndroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppAndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        val context = LocalContext.current
                        val session = remember { SessionManager(context) }

                        var showRegister by remember { mutableStateOf(false) }
                        var isLogged by remember { mutableStateOf(session.isLogged()) }

                        val loginViewModel: LoginViewModel = hiltViewModel()
                        val registerViewModel: RegisterViewModel = hiltViewModel()

                        if (!isLogged) {
                            if (showRegister) {
                                RegisterBottomSheet(
                                    registerViewModel = registerViewModel,
                                    onDismiss = { showRegister = false },
                                    onRegisterSuccess = {
                                        session.setLogged(true)
                                        isLogged = true
                                    }
                                )
                            }

                            LoginScreen(
                                isLogged = isLogged,
                                loginViewModel = loginViewModel,
                                onShowRegister = { showRegister = true },
                                onLoginSuccess = {
                                    session.setLogged(true)
                                    isLogged = true
                                },
                            )
                        } else {
                            Orchestrator()
                        }
                    }
                }
            }
        }
    }
}
