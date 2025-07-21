package com.example.remotes.dtos.order

data class OrderItemDto(
    val id: String?,
    val productName: String?,
    val price: Double?,
    val quantity: Int?
)