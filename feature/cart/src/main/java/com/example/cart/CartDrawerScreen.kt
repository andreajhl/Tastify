package com.example.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartDrawerContent(
    toggleShowCart: () -> Unit,
    cartItems: List<ProductEntity>,
    totalPrice: Double,
    onAddProduct: (Int, Int) -> Unit,
    onRestoreProduct: (Int, Int) -> Unit,
    addToCart: (ProductEntity, Int) -> Unit,
    subtractFromCart: (Int, Int) -> Unit,
    removeItemCart: (Int, (Int) -> Unit) -> Unit,
    getProductStock: (Int) -> Int
) {
    val enableAddButton = { productId: Int ->
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

    val removeItem = { productId: Int ->
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
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
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
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = cartItems,
                    key = { it.id }
                ) { product ->
                    CartItemScreen(
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

        Column(Modifier.padding(16.dp)) {
            HorizontalDivider()
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(stringResource(R.string.cart_total_price), style = MaterialTheme.typography.titleMedium)
                Text("$${totalPrice}", style = MaterialTheme.typography.titleMedium)
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CartDrawerContentPreview() {
    val mockProducts = listOf(
        ProductEntity(1, "Hamburguesa", "", 1500.0, "fast_food", "", 2, true, false, false),
        ProductEntity(2, "Ensalada", "", 1200.0, "healthy_food", "", 1, true, true, true)
    )

    MaterialTheme {
        CartDrawerContent(
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