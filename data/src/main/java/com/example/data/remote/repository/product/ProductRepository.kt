package com.example.data.remote.repository.product

import com.example.data.remote.dtos.product.ProductUpdateDto
import com.example.db.entities.ProductEntity

interface ProductRepository {
    suspend fun getAll(): List<ProductEntity>
    suspend fun getAllRemote(): List<ProductEntity>
    suspend fun updateProduct(id: String, body: ProductUpdateDto): ProductEntity?
}