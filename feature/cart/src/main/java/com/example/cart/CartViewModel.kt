package com.example.cart

import androidx.lifecycle.ViewModel
import com.example.db.entities.ProductEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface CartContract {
    val cart: StateFlow<CartState>
    fun addToCart(product: ProductEntity, quantity: Int)
    fun subtractToCart(productId: Int, quantity: Int)
    fun clearCart()
    fun removeItemCart(productId: Int, callback: (Int) -> Unit)
    fun getTotalPrice(): Double
    fun getTotalItems(): Int
    fun toggleShowCart()
}

data class CartState(
    val items: Map<Int, ProductEntity> = emptyMap(),
    val showCart: Boolean = false,
)

@HiltViewModel
class CartViewModel @Inject constructor() : ViewModel(), CartContract {

    private val _cart = MutableStateFlow(CartState())
    override val cart: StateFlow<CartState> get() = _cart

    override fun addToCart(product: ProductEntity, quantity: Int) {
        val updatedCart = _cart.value.items.toMutableMap()
        val currentProduct = updatedCart[product.id]

        if (currentProduct != null) {
            val newQuantity = currentProduct.quantity + quantity
            updatedCart[product.id] = currentProduct.copy(quantity = newQuantity)
        } else {
            updatedCart[product.id] = product.copy(quantity = quantity)
        }

        _cart.value = _cart.value.copy(items = updatedCart)
    }

    override fun subtractToCart(productId: Int, quantity: Int) {
        val updatedCart = _cart.value.items.toMutableMap()
        val currentProduct = updatedCart[productId] ?: return

        val newQty = currentProduct.quantity - quantity
        if (newQty <= 0) updatedCart.remove(productId)
        else updatedCart[productId] = currentProduct.copy(quantity = newQty)

        _cart.value = _cart.value.copy(items = updatedCart)
    }

    override fun clearCart() {
        _cart.value = _cart.value.copy(items = emptyMap())
    }

    override fun removeItemCart(productId: Int, callback: (Int) -> Unit) {
        val updatedCart = _cart.value.items.toMutableMap()
        val removedItem = updatedCart.remove(productId)

        _cart.value = _cart.value.copy(items = updatedCart)

        removedItem?.let {
            callback(it.quantity)
        }
    }

    override fun getTotalPrice(): Double {
        return _cart.value.items.values.sumOf { it.price * it.quantity }
    }

    override fun getTotalItems(): Int {
        return _cart.value.items.values.sumOf { it.quantity }
    }

    override fun toggleShowCart() {
        val newValue = !_cart.value.showCart
        _cart.value = _cart.value.copy(showCart = newValue)
    }
}