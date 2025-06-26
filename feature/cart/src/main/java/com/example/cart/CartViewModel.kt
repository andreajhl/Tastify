package com.example.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class CartState(
    val items: Map<Int, Product> = emptyMap(),
)

open class CartViewModel: ViewModel() {
    private val _cart = MutableStateFlow(CartState())
    val cart: StateFlow<CartState> get() = _cart

    open fun addToCart(product: Product, quantity: Int) {
        val updatedCart = _cart.value.items.toMutableMap()
        val currentProduct = updatedCart[product.id]

        if (currentProduct != null) {
            val newQuantity = currentProduct.quantity + quantity
            updatedCart[product.id] = currentProduct.copy(quantity = newQuantity)
        } else updatedCart[product.id] = product.copy(quantity = quantity)

        _cart.value = _cart.value.copy(items = updatedCart)
    }

    open fun subtractToCart(productId: Int, quantity: Int) {
        val updatedCart = _cart.value.items.toMutableMap()
        val currentProduct = updatedCart[productId] ?: return

        val newQty = currentProduct.quantity - quantity

        if (newQty <= 0) updatedCart.remove(productId)
        else updatedCart[productId] = currentProduct.copy(quantity = newQty)

        _cart.value = _cart.value.copy(items = updatedCart)
    }

    open fun clearCart() {
        _cart.value = _cart.value.copy(items = emptyMap())
    }

    fun removeItemCart(productId: Int, callback: (Int) -> Unit) {
        val updatedCart = _cart.value.items.toMutableMap()
        val removedItem = updatedCart.remove(productId)

        _cart.value = _cart.value.copy(items = updatedCart)

        removedItem?.let {
            callback(it.quantity)
        }
    }

    open fun getTotalPrice(): Double {
        return _cart.value.items.values.sumOf { it.price * it.quantity }
    }

    fun getTotalItems(): Int {
        return _cart.value.items.values.sumOf { it.quantity }
    }
}