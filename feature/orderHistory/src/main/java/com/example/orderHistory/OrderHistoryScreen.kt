package com.example.orderHistory

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderHistoryScreen(
    onOpenDrawer: () -> Unit,
    navController: NavHostController,
    ) {
    val orderHistoryViewModel: OrderHistoryViewModel = hiltViewModel()

    val orders by orderHistoryViewModel.orderHistoryData.collectAsState()
    val state by orderHistoryViewModel.orderHistoryState.collectAsState()

    LaunchedEffect(Unit) {
        orderHistoryViewModel.getOrderHistory()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.order_history_title),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onOpenDrawer) {
                        Icon(
                            Icons.Default.Menu,
                            contentDescription = stringResource(R.string.order_history_menu)
                        )
                    }
                },
            )
        },
    ) { padding ->
        when {
            state.isLoading -> {
                OrderHistorySkeleton(padding)
            }
            orders.isNotEmpty() -> {
                OrderHistoryContent(
                    padding = padding,
                    orders = orders,
                    navController = navController
                )
            }
            else -> {
                EmptyOrderHistory(
                    padding = padding,
                    onGoHome = {}
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrderHistoryScreenPreview() {
    val fakeNavController = rememberNavController()

    OrderHistoryScreen(
        onOpenDrawer = {},
        navController = fakeNavController
    )
}