package com.example.data.remote.repository.product

import com.example.data.remote.dtos.product.ProductUpdateDto
import com.example.db.entities.ProductEntity

interface ProductRepository {
    suspend fun getAll(): List<ProductEntity>
    suspend fun getAllRemote(): List<ProductEntity>
    suspend fun updateProductLocal(id: String, quantityToSubtract: Int)
    suspend fun updateProductRemote(id: String, body: ProductUpdateDto): ProductEntity?
}