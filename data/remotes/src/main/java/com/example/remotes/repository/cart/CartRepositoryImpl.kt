package com.example.remotes.repository.cart

import com.example.db.daos.CartDao
import com.example.db.entities.CartItemEntity
import com.example.db.entities.ProductEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao
) : CartRepository {
    private val _cartState = MutableStateFlow(CartState())
    override val cartState: StateFlow<CartState> = _cartState

    override suspend fun loadCartFromDb() {
        val itemsWithProduct = cartDao.getItems()

        val itemsMap = itemsWithProduct.associate {
            it.product.id to it.product.copy(quantity = it.cartItem.quantity)
        }

        val totalPrice = itemsMap.values.sumOf { it.price * it.quantity }

        _cartState.value = CartState(
            items = itemsMap,
            showCart = _cartState.value.showCart,
            totalPrice = totalPrice
        )
    }

    override suspend fun addToCart(product: ProductEntity, quantity: Int) {
        val existingItem = cartDao.getItemById(product.id)
        val newQuantity = (existingItem?.cartItem?.quantity ?: 0) + quantity

        cartDao.insertItem(
            CartItemEntity(
                productId = product.id,
                quantity = newQuantity,
                price = product.price
            )
        )

        loadCartFromDb()
    }

    override suspend fun subtractFromCart(productId: String, quantity: Int) {
        val existingItem = cartDao.getItemById(productId)

        if (existingItem != null) {
            val newQuantity = existingItem.cartItem.quantity - quantity

            if (newQuantity <= 0) {
                cartDao.deleteItem(
                    CartItemEntity(
                        productId = productId,
                        quantity = existingItem.cartItem.quantity,
                        price = existingItem.cartItem.price

                    )
                )
            } else {
                cartDao.insertItem(
                    CartItemEntity(
                        productId = productId,
                        quantity = newQuantity,
                        price = existingItem.cartItem.price
                    )
                )
            }

            loadCartFromDb()
        }
    }

    override suspend fun removeItem(productId: String, callback: (Int) -> Unit) {
        val existingItem = cartDao.getItemById(productId)

        existingItem?.let {
            cartDao.deleteItem(
                CartItemEntity(
                    productId = productId,
                    quantity = existingItem.cartItem.quantity,
                    price = existingItem.cartItem.price
                )
            )
            callback(it.cartItem.quantity)
            loadCartFromDb()
        } ?: callback(0)
    }

    override suspend fun clearCart() {
        cartDao.clear()
        _cartState.value = CartState(
            items = emptyMap(),
            showCart = false,
            totalPrice = 0.0
        )
    }

    override fun toggleShowCart() {
        val newValue = !_cartState.value.showCart
        _cartState.value = _cartState.value.copy(showCart = newValue)
    }
}