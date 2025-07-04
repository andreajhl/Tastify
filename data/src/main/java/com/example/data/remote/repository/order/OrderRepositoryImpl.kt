package com.example.data.remote.repository.order

import com.example.data.remote.api.OrderApi
import com.example.data.remote.dtos.order.OrderDto
import com.example.data.remote.dtos.order.OrderRequestDto
import com.example.data.remote.dtos.order.OrderResponseDto
import jakarta.inject.Inject
import retrofit2.Response

class OrderRepositoryImpl @Inject constructor(private val service: OrderApi) : OrderRepository {
    override suspend fun getOrdersByUser(id: Int): Response<List<OrderDto>> =
        service.getOrdersByUser(id)

    override suspend fun createOrder(request: OrderRequestDto): Response<OrderResponseDto> =
        service.createOrder(request)
}