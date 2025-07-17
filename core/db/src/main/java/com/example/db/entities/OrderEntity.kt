package com.example.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey()
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "total")
    val total: Double,

    @ColumnInfo(name = "timestamp")
    val timestamp: Long
)
