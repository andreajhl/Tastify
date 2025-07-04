package com.example.data.remote.repository.order

import com.example.data.remote.dtos.order.OrderDto
import com.example.data.remote.dtos.order.OrderRequestDto
import com.example.data.remote.dtos.order.OrderResponseDto
import retrofit2.Response

interface OrderRepository {
    suspend fun getOrdersByUser(id: Int): Response<List<OrderDto>>
    suspend fun createOrder(request: OrderRequestDto): Response<OrderResponseDto>

}