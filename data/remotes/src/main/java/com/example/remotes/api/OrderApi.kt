package com.example.remotes.api

import com.example.remotes.dtos.order.OrderCreateDto
import com.example.remotes.dtos.order.OrderDto
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