package com.example.useCase.productList

import android.util.Log
import com.example.db.entities.ProductEntity
import com.example.remotes.repository.product.ProductRepository
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(): Result<List<ProductEntity>> {
        return try {
            val localProducts = productRepository.getProductListLocal()

            if (localProducts.isNotEmpty()) {
                Result.success(localProducts)
            } else {
                val remoteProducts = productRepository.getProductListRemote()
                Result.success(remoteProducts)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}