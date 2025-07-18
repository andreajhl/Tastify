package com.example.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.remote.repository.cart.CartRepository
import com.example.data.remote.repository.cart.CartState
import com.example.db.entities.ProductEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

interface CartContract {
    val cart: StateFlow<CartState>

    fun getCart()
    fun addToCart(product: ProductEntity, quantity: Int)
    fun subtractToCart(productId: String, quantity: Int)
    fun removeItemCart(productId: String, callback: (Int) -> Unit)
    fun clearCart()
    fun getTotalItems(): Int
    fun toggleShowCart()
}

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel(), CartContract {
    override val cart: StateFlow<CartState> get() = cartRepository.cartState

    override fun getCart() {
        viewModelScope.launch {
            cartRepository.loadCartFromDb()
        }
    }

    override fun addToCart(product: ProductEntity, quantity: Int) {
        viewModelScope.launch {
            cartRepository.addToCart(product, quantity)
        }
    }

    override fun subtractToCart(productId: String, quantity: Int) {
        viewModelScope.launch {
            cartRepository.subtractFromCart(productId, quantity)
        }
    }

    override fun clearCart() {
        viewModelScope.launch {
            cartRepository.clearCart()
        }
    }

    override fun removeItemCart(productId: String, callback: (Int) -> Unit) {
        viewModelScope.launch {
            cartRepository.removeItem(productId, callback)
        }
    }

    override fun getTotalItems(): Int {
        return cart.value.items.values.sumOf { it.quantity }
    }

    override fun toggleShowCart() {
        cartRepository.toggleShowCart()
    }
}