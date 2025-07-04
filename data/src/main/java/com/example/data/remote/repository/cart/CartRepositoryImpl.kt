package com.example.data.remote.repository.cart

import com.example.db.daos.CartDao
import com.example.db.entities.CartItemEntity
import com.example.db.entities.CartItemWithProduct
import jakarta.inject.Inject

class CartRepositoryImpl @Inject constructor(private val dao: CartDao) : CartRepository {
    override suspend fun getItems(): List<CartItemWithProduct> = dao.getItems()
    override suspend fun getItemById(id: Int): CartItemWithProduct? = dao.getItemById(id)
    override suspend fun insertItem(item: CartItemEntity) = dao.insertItem(item)
    override suspend fun updateItem(item: CartItemEntity) = dao.updateItem(item)
    override suspend fun deleteItem(item: CartItemEntity) = dao.deleteItem(item)
    override suspend fun clear() = dao.clear()
}