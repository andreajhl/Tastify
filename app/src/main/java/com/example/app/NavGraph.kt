package com.example.app

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.authentication.AuthenticationScreen
import com.example.home.HomeScreen
import com.example.navigation.Screen
import com.example.orderDetail.OrderDetailScreen
import com.example.orderHistory.OrderHistoryScreen
import com.example.orderPay.OrderPayScreen
import com.example.profile.ProfileScreen
import com.example.session.SessionManager

@Composable
fun NavGraph(
    session: SessionManager,
    navController: NavHostController,
    openDrawer: () -> Unit
) {
    NavHost(
        navController,
        startDestination = if (session.isLogged()) "home" else "login"
    ) {
        composable(Screen.Home.route) {
            HomeScreen(openDrawer, navController)
        }
        composable(Screen.Profile.route) {
            ProfileScreen(openDrawer)
        }

        composable(Screen.OrderPay.route) {
            OrderPayScreen(navController)
        }

        composable(Screen.OrderHistory.route) {
            OrderHistoryScreen(openDrawer, navController)
        }

        composable(Screen.Login.route) {
            AuthenticationScreen(navController)
        }

        composable(
            route = Screen.OrderDetail.route,
            arguments = listOf(navArgument("orderId") { type = NavType.StringType })
        ) { backStackEntry ->
            val orderId = backStackEntry.arguments?.getString("orderId") ?: ""

            OrderDetailScreen( navController, orderId)
        }
    }
}
