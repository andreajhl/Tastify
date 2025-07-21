package com.example.remotes.dtos.order

data class OrderDto(
    val id: String,
    val userId: String,
    val items: List<OrderItemDto>,
    val total: Double,
    val timestamp: Long
)
