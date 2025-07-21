package com.example.worker.product

import androidx.work.workDataOf
import com.example.worker.helper.WorkManagerHelper
import javax.inject.Inject

class ProductSyncManager @Inject constructor(
    private val workManagerHelper: WorkManagerHelper
) {

    fun syncProduct(productId: String, quantity: Int, operation: String = "subtract") {
        workManagerHelper.scheduleOneTimeTask<ProductSyncWorker>(
            uniqueWorkName = "ProductSync_$productId",
            inputData = workDataOf(
                "PRODUCT_ID" to productId,
                "QUANTITY" to quantity,
                "OPERATION" to operation
            )
        )
    }
}