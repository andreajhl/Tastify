package com.example.orderHistory

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.db.entities.OrderEntity
import com.example.db.entities.OrderItemEntity
import com.example.db.entities.OrderItemProduct
import com.example.navigation.Screen
import com.example.theme.AppAndroidTheme
import com.example.theme.DefaultScreenPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderHistoryContent(
    padding:  PaddingValues,
    orders:  List<OrderItemProduct>,
    navController: NavHostController,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onPrimary)
            .padding(padding)
            .padding(DefaultScreenPadding),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(
            items = orders,
            key = { it.order.id }
        ) { currentOrder ->
            OrderCard(
                id = currentOrder.order.id,
                totalPrice = currentOrder.order.total,
                totalItems = currentOrder.items.size,
                date = currentOrder.order.timestamp,
                onClick = { id ->
                    val route = Screen.OrderDetail.route.replace("{orderId}", currentOrder.order.id)
                    navController.navigate(route)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrderHistoryContentPreview() {
    val fakeNavController = rememberNavController()

    val fakeOrders = listOf(
        OrderItemProduct(
            order = OrderEntity(
                id = "order_1",
                total = 150.0,
                timestamp = System.currentTimeMillis()
            ),
            items = listOf(
                OrderItemEntity(
                    id = 1,
                    orderId = "order_1",
                    productName = "Product 1",
                    price = 50.0,
                    quantity = 1
                ),
                OrderItemEntity(
                    id = 2,
                    orderId = "order_1",
                    productName = "Product 2",
                    price = 100.0,
                    quantity = 1
                )
            )
        )
    )

    AppAndroidTheme(darkTheme = true, dynamicColor = false) {
        OrderHistoryContent(
            padding = PaddingValues(12.dp),
            orders = fakeOrders,
            navController = fakeNavController
        )
    }
}