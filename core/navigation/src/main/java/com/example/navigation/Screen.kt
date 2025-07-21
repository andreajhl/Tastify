package com.example.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Details
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Profile : Screen("profile", "Profile", Icons.Default.Person)
    object OrderHistory : Screen("order_history", "Order History", Icons.Default.History)
    object OrderDetail : Screen("order/{orderId}", "Order Detail", Icons.Default.Details)
    object OrderPay : Screen("order_payment", "Order Pay", Icons.Filled.ShoppingCart)
    object Login : Screen("login", "", Icons.Default.AccountCircle)

    companion object {
        val all = listOf(Home, Profile, OrderHistory)
    }
}