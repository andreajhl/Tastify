package com.example.data.remote.api

import com.example.data.remote.dtos.order.OrderCreateDto
import com.example.data.remote.dtos.order.OrderDto
import com.example.db.entities.OrderEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderApi {
    @GET("orders/user/{id}")
    suspend fun getOrdersByUserId(@Path("id") id: String): Response<List<OrderDto>>

    @GET("orders/{id}")
    suspend fun getOrderById(@Path("id") id: String): Response<OrderDto>

    @POST("orders")
    suspend fun createOrder(@Body request: OrderCreateDto): Response<OrderDto>
}