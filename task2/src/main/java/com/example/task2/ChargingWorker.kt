package com.example.task2

import android.content.Context
import android.content.pm.ServiceInfo
import android.os.Build
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

class ChargingWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    private val notification = NotificationHelper(applicationContext).createNotification()

    override suspend fun doWork(): Result {
        try {
            setForeground(getForegroundInfo())
            delay(10000)
            return Result.success()
        } catch (e: Exception) {
            return Result.failure()
        }
    }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return getForegroundNotifyInfo()
    }

    private fun getForegroundNotifyInfo(): ForegroundInfo {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ForegroundInfo(
                NOTIFICATION_ID,
                notification,
                ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
            )
        } else {
            ForegroundInfo(
                NOTIFICATION_ID,
                notification
            )
        }
    }

    companion object {
        const val WORK_NAME = "ChargingWorker"
        private const val NOTIFICATION_ID = 101

        fun makeRequest() = OneTimeWorkRequestBuilder<ChargingWorker>()
            //.setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setConstraints(makeConstraints())
            .build()

        private fun makeConstraints() = Constraints.Builder()
            .setRequiresCharging(true)
            .build()
    }
}