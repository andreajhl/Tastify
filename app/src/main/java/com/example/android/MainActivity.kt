package com.example.android

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
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
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppAndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    val context = LocalContext.current
                    val session = remember { SessionManager(context) }

                    var showRegister by remember { mutableStateOf(false) }
                    var isLogged by remember { mutableStateOf(session.isLogged()) }

                    fun onLoginSuccess() {
                        session.setLogged(true)
                        isLogged = true
                    }


                    session.clearSession()
                    session.setLogged(false)

                    if (!isLogged) {
                        if (showRegister) {
                            RegisterBottomSheet(
                                onDismiss = { showRegister = false },
                                onRegisterSuccess = { onLoginSuccess() }
                            )
                        }

                        LoginScreen(
                            isLogged = isLogged,
                            onShowRegister = { showRegister = true },
                            onLoginSuccess = { onLoginSuccess() },
                        )
                    } else {
                        Orchestrator()
                    }
                }
            }
        }
    }
}
