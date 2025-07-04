package com.example.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Inicio", Icons.Default.Home)
    object Profile : Screen("profile", "Perfil", Icons.Default.Person)
    object History : Screen("order-history", "Historial", Icons.Default.History)

    companion object {
        val all = listOf(Home, Profile, History)
    }
}