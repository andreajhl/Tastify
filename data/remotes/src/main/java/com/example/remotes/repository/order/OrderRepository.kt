package com.example.remotes.repository.order

import com.example.db.entities.OrderEntity
import com.example.db.entities.OrderItemProduct
import com.example.remotes.dtos.order.OrderCreateDto

interface OrderRepository {
    suspend fun getOrdersRemote(id: String): List<OrderItemProduct>
    suspend fun createOrderLocal(request: OrderCreateDto): OrderEntity?
    suspend fun getOrdersLocal(id: String): List<OrderItemProduct>
    suspend fun getOrder(id: String): OrderItemProduct?
    suspend fun createOrderRemote(orderId: String, userId: String): Boolean
}