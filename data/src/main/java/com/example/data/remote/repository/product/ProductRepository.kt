package com.example.data.remote.repository.product

import com.example.db.entities.ProductEntity

interface ProductRepository {
    suspend fun getAll(): List<ProductEntity>
    suspend fun getAllRemote(): List<ProductEntity>
}