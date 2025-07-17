package com.example.data.remote.repository.order

import com.example.data.remote.dtos.order.OrderCreateDto
import com.example.db.entities.OrderEntity
import com.example.db.entities.OrderItemProduct

interface OrderRepository {
    suspend fun getAllRemote(id: String): List<OrderItemProduct>
    suspend fun createOrder(request: OrderCreateDto): OrderEntity?
    suspend fun getAll(): List<OrderItemProduct>
    suspend fun getOrder(id: String): OrderItemProduct?
}