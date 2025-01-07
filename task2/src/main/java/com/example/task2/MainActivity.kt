package com.example.task2

import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager

class MainActivity : AppCompatActivity() {
    private val notificationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { if (it) scheduleChargingCheck() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (NotificationHelper.checkNotificationPermission(applicationContext)) {
            notificationPermissionRequest.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        } else {
            scheduleChargingCheck()
        }
    }

    private fun scheduleChargingCheck() {
        WorkManager
            .getInstance(applicationContext)
            .enqueueUniqueWork(
                ChargingWorker.WORK_NAME,
                ExistingWorkPolicy.REPLACE,
                ChargingWorker.makeRequest()
            )
    }
}