package com.example.data.remote.dtos.order

data class OrderItemDto(
    val id: String?,
    val productName: String?,
    val price: Double?,
    val quantity: Int?
)