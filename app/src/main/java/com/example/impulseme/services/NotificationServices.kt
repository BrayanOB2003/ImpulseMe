package com.example.impulseme.services

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.impulseme.R

class NotificationServices(
    private val context: Context,
    private val channelId: String
) {
    init {
        createNotificationChannel()
    }

    private var builderSimpleNotification = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle("My notification")
        .setContentText("Notificación de prueba...")
        .setStyle(
            NotificationCompat.BigTextStyle()
                .bigText("Notificación de prueba..."))
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    private fun createNotificationChannel() {

        val name = "simple_notification"
        val descriptionText = "A simple notification with text"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelId, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system.
        val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun checkPermission(): NotificationManagerCompat? {
        val notificationManager = NotificationManagerCompat.from(context)

        return if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ){
            notificationManager
        } else {
            null
        }
    }

    fun createSimpleNotification(id: Int, title: String, content: String) {
        val notificationManager =  checkPermission()
        builderSimpleNotification.setContentTitle(title)
        builderSimpleNotification.setContentText(content)
        builderSimpleNotification.setStyle(
            NotificationCompat.BigTextStyle()
                .bigText(content))

        notificationManager?.notify(id, builderSimpleNotification.build())
    }

}