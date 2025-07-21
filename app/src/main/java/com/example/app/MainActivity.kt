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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.common.CustomSnackbar
import com.example.library.utils.SnackbarManager
import com.example.menu.Menu
import com.example.navigation.Screen
import com.example.session.SessionManager
import com.example.theme.AppAndroidTheme
import com.example.worker.productList.ProductListSyncManager
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var productListSyncManager: ProductListSyncManager

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val snackbarHostState = SnackbarHostState()
        val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

        productListSyncManager.schedulePeriodicSync()

        productListSyncManager.syncNow()

        enableEdgeToEdge()
        setContent {
            AppAndroidTheme(
                darkTheme = isSystemInDarkTheme(),
                dynamicColor = false
            ) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { CustomSnackbar(hostState = snackbarHostState) }
                ) {
                    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                    val coroutineScope = rememberCoroutineScope()
                    val navController = rememberNavController()

                    val context = LocalContext.current
                    val session = remember { SessionManager(context) }

                    fun openDrawer() {
                        coroutineScope.launch { drawerState.open() }
                    }

                    fun closeDrawer() {
                        coroutineScope.launch { drawerState.close() }
                    }

                    fun onSelectItem() {
                        coroutineScope.launch { drawerState.close() }
                    }

                    Menu(
                        navController = navController,
                        drawerState = drawerState,
                        onSelectItem = { onSelectItem() },
                        onLogout = {
                            closeDrawer()
                            session.clearSession()
                            navController.navigate(Screen.Login.route) {
                                popUpTo(0)
                            }
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


        scope.launch {
            SnackbarManager.messages.collect { snackbarMessage ->
                val result = snackbarHostState.showSnackbar(
                    duration = SnackbarDuration.Long,
                    message = snackbarMessage.message,
                    actionLabel = snackbarMessage.actionLabel
                )

                if (result == SnackbarResult.ActionPerformed) {
                    snackbarMessage.onAction?.invoke()
                }
            }
        }
    }
}
