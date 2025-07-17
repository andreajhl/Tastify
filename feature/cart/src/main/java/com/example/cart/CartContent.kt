package com.example.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.db.entities.ProductEntity
import com.example.theme.ui.theme.MainColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartContent(
    goToPay: () -> Unit,
    toggleShowCart: () -> Unit,
    cartItems: List<ProductEntity>,
    totalPrice: Double,
    onAddProduct: (String, Int) -> Unit,
    onRestoreProduct: (String, Int) -> Unit,
    addToCart: (ProductEntity, Int) -> Unit,
    subtractFromCart: (String, Int) -> Unit,
    removeItemCart: (String, (Int) -> Unit) -> Unit,
    getProductStock: (String) -> Int
) {
    val enableAddButton = { productId: String ->
        getProductStock(productId) - 1 >= 0
    }

    val incrementItem = { product: ProductEntity ->
        onAddProduct(product.id, 1)
        addToCart(product, 1)
    }

    val subtractItem = { product: ProductEntity ->
        subtractFromCart(product.id, 1)
        onRestoreProduct(product.id, 1)
    }

    val removeItem = { productId: String ->
        removeItemCart(productId) { quantity ->
            onRestoreProduct(productId, quantity)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(Color.White),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = toggleShowCart) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.cart_button)
                    )
                }

                Text(
                    text = stringResource(R.string.cart_title),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            HorizontalDivider()

            LazyColumn(
                modifier = Modifier.padding(top = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(
                    items = cartItems,
                    key = { it.id }
                ) { product ->
                    CartItem(
                        name = product.name,
                        imageUrl = product.imageUrl,
                        price = product.price,
                        quantity = product.quantity,
                        incrementItem = { incrementItem(product) },
                        subtractItem = { subtractItem(product) },
                        removeItem = { removeItem(product.id) },
                        enableAddButton = enableAddButton(product.id)
                    )
                }
            }
        }

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            HorizontalDivider()

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(stringResource(R.string.cart_total_price), style = MaterialTheme.typography.titleMedium)
                Text("$${totalPrice}", style = MaterialTheme.typography.titleMedium)
            }

            Button(
                onClick = { goToPay() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MainColor)
            ) {
                Text(
                    stringResource(R.string.cart_pay),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CartContentPreview() {
    val mockProducts = listOf(
        ProductEntity("1", "Hamburger", "", 1500.0, "fast_food", "", 2, true, false, false),
        ProductEntity("2", "Salad", "", 1200.0, "healthy_food", "", 1, true, true, true)
    )

    MaterialTheme {
        CartContent(
            goToPay = {},
            toggleShowCart = {},
            cartItems = mockProducts,
            totalPrice = mockProducts.sumOf { it.price * it.quantity },
            onAddProduct = { _, _ -> },
            onRestoreProduct = { _, _ -> },
            addToCart = { _, _ -> },
            subtractFromCart = { _, _ -> },
            removeItemCart = { _, _ -> },
            getProductStock = { _ -> 1 }
        )
    }
}