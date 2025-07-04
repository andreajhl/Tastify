package com.example.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.db.entities.OrderEntity

@Dao
interface OrderDao {
    @Query("SELECT * FROM orders order by id desc LIMIT 1 ")
    suspend fun getOrderByUser(): OrderEntity

    @Query("SELECT * FROM orders")
    suspend fun getAllOrders(): List<OrderEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createOrder(item: OrderEntity)

    @Update
    suspend fun updateOrder(order: OrderEntity)
}