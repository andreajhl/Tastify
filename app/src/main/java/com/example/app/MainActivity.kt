package com.example.app

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.menu.Menu
import com.example.navigation.Screen
import com.example.session.SessionManager
import com.example.theme.ui.theme.AppAndroidTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppAndroidTheme(
                darkTheme = isSystemInDarkTheme(),
                dynamicColor = false
            ) {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                    val coroutineScope = rememberCoroutineScope()
                    val navController = rememberNavController()

                    val context = LocalContext.current
                    val session = remember { SessionManager(context) }

                    fun openDrawer() {
                        coroutineScope.launch { drawerState.open() }
                    }

                    fun onSelectItem() {
                        coroutineScope.launch { drawerState.close() }
                    }

                    Menu(
                        navController = navController,
                        drawerState = drawerState,
                        onSelectItem = { onSelectItem() },
                        onLogout = {
                            session.clearSession()
                            navController.navigate(Screen.Login.route)
                        }
                    ) {
                        Box(Modifier.fillMaxSize()) {
                            Scaffold(
                                content = {
                                    NavGraph(
                                        session = session,
                                        navController = navController,
                                        openDrawer = { openDrawer() }
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
