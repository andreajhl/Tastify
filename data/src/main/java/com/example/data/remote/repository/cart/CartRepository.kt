package com.example.data.remote.repository.cart

import com.example.db.entities.CartItemEntity
import com.example.db.entities.CartItemWithProduct


interface CartRepository {
    suspend fun getItems(): List<CartItemWithProduct>
    suspend fun getItemById(id: Int): CartItemWithProduct?
    suspend fun insertItem(item: CartItemEntity)
    suspend fun updateItem(item: CartItemEntity)
    suspend fun deleteItem(item: CartItemEntity)
    suspend fun clear()
}