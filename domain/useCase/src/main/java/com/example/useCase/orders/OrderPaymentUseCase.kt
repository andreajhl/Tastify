package com.example.useCase.orders

import com.example.remotes.dtos.order.OrderCreateDto
import com.example.remotes.dtos.order.OrderItemDto
import com.example.remotes.repository.cart.CartRepository
import com.example.remotes.repository.order.OrderRepository
import com.example.remotes.repository.product.ProductRepository
import com.example.session.SessionManager
import com.example.worker.orders.OrderSyncManager
import com.example.worker.product.ProductSyncManager
import javax.inject.Inject

class OrderPaymentUseCase @Inject constructor(
    private val productRepository: ProductRepository,
    private val orderRepository: OrderRepository,
    private val cartRepository: CartRepository,
    private val sessionManager: SessionManager,
    private val productSyncManager: ProductSyncManager,
    private val orderSyncManager: OrderSyncManager
) {
    suspend operator fun invoke(): Result<Unit> {
        return try {
            val cartItems = cartRepository.cartState.value.items

            cartRepository.toggleShowCart()

            val orderItems = cartItems.values.map { product ->
                OrderItemDto(
                    id = product.id,
                    productName = product.name,
                    quantity = product.quantity,
                    price = product.price
                )
            }

            val order = orderRepository.createOrderLocal(
                OrderCreateDto(
                    userId = sessionManager.getUserId()!!,
                    items = orderItems,
                    total = cartRepository.cartState.value.totalPrice,
                    timestamp = System.currentTimeMillis()
                )
            )

            if (order != null) {
                orderSyncManager.syncOrder(order.id)
            }

            cartItems.values.forEach { product ->
                val quantityToSubtract = product.quantity
                productRepository.updateProductLocal(product.id, quantityToSubtract)

                productSyncManager.syncProduct(
                    productId = product.id,
                    quantity = quantityToSubtract
                )
            }

            cartRepository.clearCart()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}