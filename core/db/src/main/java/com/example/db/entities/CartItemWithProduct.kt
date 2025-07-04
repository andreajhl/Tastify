package com.example.db.entities

import androidx.room.Embedded
import androidx.room.Relation

data class CartItemWithProduct(
    @Embedded val cartItem: CartItemEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val product: ProductEntity
)
