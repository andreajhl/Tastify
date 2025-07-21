package com.example.useCase.orders

import com.example.db.entities.OrderItemProduct
import com.example.remotes.repository.order.OrderRepository
import javax.inject.Inject

class GetOrderDetailUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    suspend operator fun invoke(id: String): Result<OrderItemProduct?> {
        return try {
            val order = orderRepository.getOrder(id)
            Result.success(order)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}