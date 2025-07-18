package com.example.data.remote.repository.cart

import com.example.db.entities.ProductEntity
import kotlinx.coroutines.flow.StateFlow

data class CartState(
    val totalPrice: Double = 0.0,
    val items: Map<String, ProductEntity> = emptyMap(),
    val showCart: Boolean = false,
)

interface CartRepository {
    val cartState: StateFlow<CartState>
    suspend fun loadCartFromDb()
    suspend fun addToCart(product: ProductEntity, quantity: Int)
    suspend fun subtractFromCart(productId: String, quantity: Int)
    suspend fun removeItem(productId: String, callback: (Int) -> Unit)
    fun toggleShowCart()
    suspend fun clearCart()
}