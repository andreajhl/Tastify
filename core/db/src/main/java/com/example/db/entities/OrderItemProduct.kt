package com.example.db.entities

import androidx.room.Embedded
import androidx.room.Relation

data class OrderItemProduct(
    @Embedded val order: OrderEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "orderId"
    )
    val items: List<OrderItemEntity>
)