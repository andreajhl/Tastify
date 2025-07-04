package com.example.menu

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.home.Home
import com.example.navigation.Screen
import com.example.profile.Profile

@Composable
fun NavGraph(navController: NavHostController, openDrawer: () -> Unit) {
    NavHost(navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            Home(openDrawer)
        }
        composable(Screen.Profile.route) { Profile(openDrawer) }
//        composable(Screen.History.route) { }
    }
}