package com.example.worker.orders

import androidx.work.workDataOf
import com.example.worker.helper.WorkManagerHelper
import javax.inject.Inject

class OrderSyncManager @Inject constructor(
    private val workManagerHelper: WorkManagerHelper
) {

    fun syncOrder(orderId: String) {
        workManagerHelper.scheduleOneTimeTask<OrderSyncWorker>(
            uniqueWorkName = "OrderSync_$orderId",
            inputData = workDataOf("ORDER_ID" to orderId)
        )
    }
}