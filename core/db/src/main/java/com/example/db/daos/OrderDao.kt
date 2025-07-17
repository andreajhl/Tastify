package com.example.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.db.entities.OrderEntity
import com.example.db.entities.OrderItemEntity
import com.example.db.entities.OrderItemProduct

@Dao
interface OrderDao {
    @Query("SELECT * FROM orders order by id desc LIMIT 1 ")
    suspend fun getOrderByUserId(): OrderEntity

    @Query("SELECT * FROM orders order by id desc LIMIT 1 ")
    suspend fun getOrderById(): OrderEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createOrder(item: OrderEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createOrderItems(items: List<OrderItemEntity>)

    @Update
    suspend fun updateOrder(order: OrderEntity)

    @Transaction
    @Query("SELECT * FROM orders WHERE id = :orderId")
    suspend fun getOrderWithItems(orderId: String): OrderItemProduct

    @Transaction
    @Query("SELECT * FROM orders")
    suspend fun getAllOrdersWithItems(): List<OrderItemProduct>
}