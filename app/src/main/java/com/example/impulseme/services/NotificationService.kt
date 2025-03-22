package com.example.impulseme.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.graphics.BitmapFactory
import com.example.impulseme.R

class NotificationService : Service() {
    private val notificationHelper by lazy { NotificationBuildServices(this, CHANNEL_ID) }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("com.example.impulseme.services.NotificationService", "Ejecutando servicio de notificación...")

        fetchDataAndShowNotification()

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun fetchDataAndShowNotification() {
        // Aquí puedes obtener datos desde un servidor antes de notificar
        val title = "¡Es hora de tu recordatorio!"
        val content = "Revisa tu aplicación para más detalles."

        notificationHelper.createSimpleNotification(NOTIFICATION_ID, title, content)

        stopSelf()
    }

    companion object {
        private const val CHANNEL_ID = "notificaciones_channel"
        private var NOTIFICATION_ID = Math.random().toInt() * 10
    }
}
