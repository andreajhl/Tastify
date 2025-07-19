package com.example.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.data.remote.dtos.product.ProductUpdateDto
import com.example.data.remote.repository.product.ProductRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject


@HiltWorker
class ProductSyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val productRepository: ProductRepository
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val productId = inputData.getString("PRODUCT_ID") ?: return Result.failure()
            val quantity = inputData.getInt("QUANTITY", 0)
            val operation = inputData.getString("OPERATION") ?: "subtract"

            if (quantity < 0) return Result.failure()

            val dto = ProductUpdateDto(quantity, operation)
            val updated = productRepository.updateProductRemote(productId, dto)

            if (updated != null) Result.success() else Result.retry()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}