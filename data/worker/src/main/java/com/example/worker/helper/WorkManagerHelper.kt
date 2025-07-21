package com.example.worker.helper

import android.content.Context
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkManagerHelper @Inject constructor(@ApplicationContext private val context: Context) {
    private val workManager = WorkManager.getInstance(context)

    internal inline fun <reified T : CoroutineWorker> schedulePeriodicTask(
        uniqueWorkName: String,
        repeatInterval: Long = 15,
        timeUnit: TimeUnit = TimeUnit.MINUTES,
        networkRequired: Boolean = true
    ) {
        val constraints = Constraints.Builder().apply {
            if (networkRequired) {
                setRequiredNetworkType(NetworkType.CONNECTED)
            }
        }.build()

        val periodicWorkRequest = PeriodicWorkRequestBuilder<T>(
            repeatInterval, timeUnit
        )
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniquePeriodicWork(
            uniqueWorkName,
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWorkRequest
        )
    }

    internal inline fun <reified T : CoroutineWorker> scheduleOneTimeTask(
        uniqueWorkName: String? = null,
        networkRequired: Boolean = true,
        inputData: Data? = null,
    ) {
        val constraints = Constraints.Builder().apply {
            if (networkRequired) {
                setRequiredNetworkType(NetworkType.CONNECTED)
            }
        }.build()

        val requestBuilder = OneTimeWorkRequestBuilder<T>()
            .setConstraints(constraints)

        if (inputData != null) {
            requestBuilder.setInputData(inputData)
        }

        val workRequest = requestBuilder.build()

        if (uniqueWorkName != null) {
            workManager.enqueueUniqueWork(
                uniqueWorkName,
                ExistingWorkPolicy.REPLACE,
                workRequest
            )
        } else {
            workManager.enqueue(workRequest)
        }
    }

    fun cancelWork(uniqueWorkName: String) {
        workManager.cancelUniqueWork(uniqueWorkName)
    }
}