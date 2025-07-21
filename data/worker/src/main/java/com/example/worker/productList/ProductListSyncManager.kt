package com.example.worker.productList

import com.example.worker.helper.WorkManagerHelper
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ProductListSyncManager @Inject constructor(
    private val workManagerHelper: WorkManagerHelper
) {

    fun schedulePeriodicSync() {
        workManagerHelper.schedulePeriodicTask<ProductListSyncWorker>(
            uniqueWorkName = "PRODUCT_SYNC_WORK",
            repeatInterval = 6,
            timeUnit = TimeUnit.HOURS
        )
    }

    fun syncNow() {
        workManagerHelper.scheduleOneTimeTask<ProductListSyncWorker>(
            uniqueWorkName = "PRODUCT_SYNC_NOW"
        )
    }
}