package com.example.remotes.repository.product

import com.example.db.entities.ProductEntity
import com.example.remotes.dtos.product.ProductUpdateDto

interface ProductRepository {
    suspend fun getProductListLocal(): List<ProductEntity>
    suspend fun getProductListLocalRemote(): List<ProductEntity>
    suspend fun updateProductLocal(id: String, quantityToSubtract: Int)
    suspend fun updateProductRemote(id: String, body: ProductUpdateDto): ProductEntity?
}