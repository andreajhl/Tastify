package com.example.cart

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.db.entities.ProductEntity
import com.example.theme.AppAndroidTheme

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

    if(cartItems.isEmpty()) {
        EmptyCart(toggleShowCart = toggleShowCart)
        return
    }

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
}

@Preview(showBackground = true)
@Composable
fun CartDrawerContentPreview() {
    val mockProducts = listOf(
        ProductEntity("1", "Hamburger", "", 1500.0, "fast_food", "", 2, true, false, false),
        ProductEntity("2", "Salad", "", 1200.0, "healthy_food", "", 1, true, true, true)
    )

    AppAndroidTheme(darkTheme = false, dynamicColor = false) {
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