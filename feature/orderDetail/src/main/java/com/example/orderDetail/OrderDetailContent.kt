package com.example.orderDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.db.entities.OrderEntity
import com.example.db.entities.OrderItemEntity
import com.example.db.entities.OrderItemProduct
import com.example.library.utils.formatTimestampToDate
import com.example.theme.ui.theme.DefaultScreenPadding
import com.example.theme.ui.theme.MainColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailContent(
    order: OrderItemProduct,
    padding: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(DefaultScreenPadding),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MainColor),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 16.dp),
                        text = "Order #${order.order.id.takeLast(6)}",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        modifier = Modifier.padding(end = 16.dp, top = 16.dp, bottom = 16.dp),
                        text = formatTimestampToDate(order.order.timestamp),
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                        color = Color.White
                    )
                }

                order.items.forEach { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${item.productName} x${item.quantity}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "$${item.price * item.quantity}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                HorizontalDivider()

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Total:",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "$${order.order.total}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun OrderDetailContentPreview() {
    val fakeOrder = OrderItemProduct(
        order = OrderEntity(
            id = "123456",
            total = 99.99,
            timestamp = System.currentTimeMillis()
        ),
        items = listOf(
            OrderItemEntity(
                id = 1,
                orderId = "123456",
                productName = "Pizza",
                price = 50.0,
                quantity = 1
            ),
            OrderItemEntity(
                id = 2,
                orderId = "123456",
                productName = "Soda",
                price = 2.5,
                quantity = 2
            )
        )
    )

    OrderDetailContent(order = fakeOrder, padding = PaddingValues(12.dp))
}