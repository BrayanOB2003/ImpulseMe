package com.example.impulseme.services.alarm

import com.example.impulseme.services.NotificationService
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        Log.d("AlarmReceiver", "Alarma recibida, iniciando com.example.impulseme.services.NotificationService...")

        val serviceIntent = Intent(context, NotificationService::class.java)
        context.startService(serviceIntent)
    }
}