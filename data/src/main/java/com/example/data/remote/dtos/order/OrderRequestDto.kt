package com.example.data.remote.dtos.order

data class OrderRequestDto(val userId: Int, val items: List<OrderItemDto>)
