package com.example.cart

import android.util.Log
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.model.Product
import com.example.productList.ProductListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartDrawerContent(
    toggleShowCart: () -> Unit,
    cartItems: List<Product>,
    cartViewModel: CartViewModel,
    productListViewModel: ProductListViewModel
) {
    val enableAddButton = { productId: Int ->
        val available = productListViewModel.getProduct(productId)?.quantity ?: 0
        available - 1 >= 0
    }

    val incrementItem = { product: Product ->
        Log.d("test-product click", "click")
        productListViewModel.subtractProduct(product.id, 1)
        cartViewModel.addToCart(product, 1)
    }

    val subtractItem = { product: Product ->
        cartViewModel.subtractToCart(product.id, 1)
        productListViewModel.addProduct(product.id, 1)
    }

    val removeItem: (Int) -> Unit = { productId ->
        cartViewModel.removeItemCart(productId) { quantity ->
            productListViewModel.addProduct(productId, quantity)
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
                IconButton(onClick = { toggleShowCart() }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close cart"
                    )
                }

                Text(
                    text = "Shopping Cart",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            HorizontalDivider()

            LazyColumn(modifier = Modifier.padding(top = 20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
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
                Text("Total:", style = MaterialTheme.typography.titleMedium)
                Text("$${cartViewModel.getTotalPrice()}", style = MaterialTheme.typography.titleMedium)
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CartDrawerContentPreview() {
    val mockProducts = listOf(
        Product(1, "Hamburguesa", "", 1500.0, true, 2, false, false, "", "fast_food"),
        Product(2, "Ensalada", "", 1200.0, true, 1, true, true, "", "healthy_food")
    )

    val fakeCartViewModel = object : CartViewModel() {
        override fun clearCart() {}
        override fun getTotalPrice(): Double = mockProducts.sumOf { it.price * it.quantity }
    }

    val fakeProductListViewModel = object : ProductListViewModel() {
        override fun getProduct(productId: Int): Product? = mockProducts.find { it.id == productId }
        override fun subtractProduct(productId: Int, quantity: Int) {}
        override fun addProduct(productId: Int, quantity: Int) {}
    }

    MaterialTheme {
        CartDrawerContent(
            toggleShowCart = {},
            cartItems = mockProducts,
            cartViewModel = fakeCartViewModel,
            productListViewModel = fakeProductListViewModel
        )
    }
}