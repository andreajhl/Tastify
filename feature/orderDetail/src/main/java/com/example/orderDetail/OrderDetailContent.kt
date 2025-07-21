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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.db.entities.OrderEntity
import com.example.db.entities.OrderItemEntity
import com.example.db.entities.OrderItemProduct
import com.example.library.utils.formatTimestampToDate
import com.example.theme.AppAndroidTheme
import com.example.theme.DefaultScreenPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailContent(
    order: OrderItemProduct,
    padding: PaddingValues
) {
    val cardContainerColor = MaterialTheme.colorScheme.surface
    val headerBackgroundColor = MaterialTheme.colorScheme.primary
    val headerTextColor = MaterialTheme.colorScheme.onPrimary

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(DefaultScreenPadding)
            .background(MaterialTheme.colorScheme.onPrimary),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.elevatedCardColors(containerColor = cardContainerColor),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(headerBackgroundColor),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 16.dp),
                        text = "Order #${order.order.id.takeLast(6)}",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = headerTextColor
                    )
                    Text(
                        modifier = Modifier.padding(end = 16.dp, top = 16.dp, bottom = 16.dp),
                        text = formatTimestampToDate(order.order.timestamp),
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                        color = headerTextColor
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
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "$${item.price * item.quantity}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Total:",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "$${order.order.total}",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
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

    AppAndroidTheme(darkTheme = true, dynamicColor = false) {
        OrderDetailContent(order = fakeOrder, padding = PaddingValues(12.dp))
    }
}
