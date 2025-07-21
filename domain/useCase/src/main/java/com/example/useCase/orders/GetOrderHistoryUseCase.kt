package com.example.useCase.orders

import com.example.db.entities.OrderItemProduct
import com.example.remotes.repository.order.OrderRepository
import com.example.session.SessionManager
import javax.inject.Inject

class GetOrderHistoryUseCase @Inject constructor(
    private val orderRepository: OrderRepository,
    private val sessionManager: SessionManager
) {
    suspend operator fun invoke(): Result<List<OrderItemProduct>> {
        return try {
            val userId = sessionManager.getUserId() ?: return Result.failure(
                IllegalStateException("No user ID found")
            )

            val localOrders = orderRepository.getOrdersLocal(userId)

            if (localOrders.isNotEmpty()) {
                Result.success(localOrders)
            } else {
                val remoteOrders = orderRepository.getOrdersRemote(userId)
                Result.success(remoteOrders)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}