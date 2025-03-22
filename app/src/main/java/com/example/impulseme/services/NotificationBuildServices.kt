package com.example.impulseme.services

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.impulseme.R

class NotificationBuildServices(
    private val context: Context,
    private val channelId: String
) {
    init {
        createNotificationChannel()
    }

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
        ) {
            notificationManager
        } else {
            Log.w("NotificationServices", "No permission for notifications")
            null
        }
    }

    fun createSimpleNotification(id: Int, title: String, content: String) {
        val notificationManager =  checkPermission()
        val builderNotification = getNotificationBuilder()
            .setContentTitle(title)
            .setContentText(content)
        notificationManager?.notify(id, builderNotification.build())
    }

    fun createImageNotification(id: Int, title: String, content: String, image: Bitmap) {
        val notificationManager =  checkPermission()
        val builderNotification = getNotificationBuilder()
            .setContentTitle(title)
            .setContentText(content)
            .setLargeIcon(image)
            .setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(image)
                    .bigLargeIcon(null as Bitmap?))
        notificationManager?.notify(id, builderNotification.build())
    }

    fun createLargeTextNotification(id: Int, title: String, content: String) {
        val notificationManager =  checkPermission()
        val builderNotification = getNotificationBuilder()
            .setContentTitle(title)
            .setContentText(content)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(content))
        notificationManager?.notify(id, builderNotification.build())
    }

    private fun getNotificationBuilder(): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
    }
}