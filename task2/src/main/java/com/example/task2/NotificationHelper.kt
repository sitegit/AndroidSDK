package com.example.task2

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

class NotificationHelper(private val context: Context) {

    fun createNotification(): Notification {
        if (checkNotificationPermission(context)) return Notification()

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.battery_charging)
            .setContentTitle("Device Charging")
            .setContentText("Device is now charging")
            .setOngoing(true)
            .setAutoCancel(true)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
        return builder.build()
    }

    companion object {
        private const val CHANNEL_ID = "charging_channel"
        private const val CHANNEL_NAME = "Charging Status"

        fun checkNotificationPermission(context: Context): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
        }
    }
}