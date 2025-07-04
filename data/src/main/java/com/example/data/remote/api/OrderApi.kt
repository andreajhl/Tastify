package com.example.data.remote.api

import com.example.data.remote.dtos.order.OrderDto
import com.example.data.remote.dtos.order.OrderRequestDto
import com.example.data.remote.dtos.order.OrderResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderApi {
    @GET("orders/{id}")
    suspend fun getOrdersByUser(@Path("id") id: Int): Response<List<OrderDto>>

    @POST("order")
    suspend fun createOrder(@Body request: OrderRequestDto): Response<OrderResponseDto>
}