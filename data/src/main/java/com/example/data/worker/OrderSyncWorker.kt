package com.example.data.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.data.remote.repository.order.OrderRepository
import com.example.session.SessionManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class OrderSyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: OrderRepository,
    private val session: SessionManager,
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            Log.d("WORKER", "ENTER")
            val orderId = inputData.getString("ORDER_ID") ?: return Result.failure()
            val userId = session.getUserId() ?: return Result.failure()

            val success = repository.createOrderRemote(orderId, userId)

            if (success) Result.success() else Result.retry()
        } catch (e: Exception) {
            Log.d("WORKER", "ERROR")
            e.printStackTrace()
            Result.retry()
        }
    }
}