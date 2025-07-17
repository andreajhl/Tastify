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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.db.entities.ProductEntity
import com.example.theme.ui.theme.MainColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartDrawerContent(
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
    if(cartItems.isNotEmpty()) {
        CartContent(
            goToPay = goToPay,
            totalPrice = totalPrice,
            getProductStock = getProductStock,
            removeItemCart = removeItemCart,
            subtractFromCart = subtractFromCart,
            addToCart = addToCart,
            onRestoreProduct = onRestoreProduct,
            onAddProduct = onAddProduct,
            toggleShowCart = toggleShowCart,
            cartItems = cartItems,
        )
    } else {
        EmptyCart(toggleShowCart = toggleShowCart)
    }
}

@Preview(showBackground = true)
@Composable
fun CartDrawerContentPreview() {
    val mockProducts = listOf(
        ProductEntity("1", "Hamburger", "", 1500.0, "fast_food", "", 2, true, false, false),
        ProductEntity("2", "Salad", "", 1200.0, "healthy_food", "", 1, true, true, true)
    )

    MaterialTheme {
        CartDrawerContent(
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