package com.example.db.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.db.entities.CartItemEntity
import com.example.db.entities.CartItemProduct

@Dao
interface CartDao {
    @Transaction
    @Query("SELECT * FROM cartItem")
    suspend fun getItems(): List<CartItemProduct>

    @Transaction
    @Query("SELECT * FROM cartItem WHERE userId = :userId")
    suspend fun getItemByUserId(userId: String): List<CartItemProduct>

    @Transaction
    @Query("SELECT * FROM cartItem WHERE id = :productId AND userId = :userId LIMIT 1")
    suspend fun getItemById(productId: String, userId: String): CartItemProduct?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: CartItemEntity)

    @Update
    suspend fun updateItem(item: CartItemEntity)

    @Delete
    suspend fun deleteItem(item: CartItemEntity)

    @Query("DELETE FROM cartItem WHERE userId = :userId")
    suspend fun clear(userId: String)
}