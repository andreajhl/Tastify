package com.example.orchestrator

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.menu.Menu
import com.example.menu.NavGraph
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Orchestrator() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()

    fun openDrawer() {
        coroutineScope.launch { drawerState.open() }
    }

    fun onSelectItem() {
        coroutineScope.launch { drawerState.close() }
    }

    Menu(
        navController = navController,
        drawerState = drawerState,
        onSelectItem = { onSelectItem() }
    ) {
        Box(Modifier.fillMaxSize()) {
            Scaffold(
                content = {
                    NavGraph(
                    navController = navController,
                    openDrawer = { openDrawer() }
                )}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrchestratorPreview() {
    Orchestrator()
}