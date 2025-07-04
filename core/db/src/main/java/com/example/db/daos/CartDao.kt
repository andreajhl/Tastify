package com.example.db.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.db.entities.CartItemEntity
import com.example.db.entities.CartItemWithProduct

@Dao
interface CartDao {
    @Transaction
    @Query("SELECT * FROM cartItem")
    suspend fun getItems(): List<CartItemWithProduct>

    @Transaction
    @Query("SELECT * FROM cartItem WHERE id = :productId LIMIT 1")
    suspend fun getItemById(productId: Int): CartItemWithProduct?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: CartItemEntity)

    @Update
    suspend fun updateItem(item: CartItemEntity)

    @Delete
    suspend fun deleteItem(item: CartItemEntity)

    @Query("DELETE FROM cartItem")
    suspend fun clear()
}