package com.example.impulseme.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat
import com.example.impulseme.services.alarm.AlarmReceiver
import java.util.Calendar

class ScheduleAlarmService {
    fun scheduleExactAlarm(context: Context, hour: Int, minute: Int, requestCode: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Verificar permisos en Android 12+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
            Log.e("AlarmService", "No se tienen permisos para alarmas exactas.")
            Toast.makeText(context, "No tienes permisos para alarmas exactas.", Toast.LENGTH_SHORT).show()
            return
        }

        val pendingIntent = getPendingIntent(context, requestCode)

        // Configurar la hora de la alarma
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            if (before(Calendar.getInstance())) add(Calendar.DAY_OF_MONTH, 1) // Si la hora pasó, programa para mañana
        }

        try {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
            Log.d("AlarmService", "Alarma exacta programada para: ${calendar.time}")
            Toast.makeText(context, "Alarma programada para las $hour:$minute", Toast.LENGTH_SHORT).show()
        } catch (e: SecurityException) {
            Log.e("AlarmService", "Error: No se tienen permisos para alarmas exactas", e)
        }
    }

    /**
     * Programa una alarma INEXACTA que se repite en un intervalo determinado.
     * - Útil para alarmas de bajo consumo de energía.
     */
    fun scheduleInexactRepeatingAlarm(context: Context, intervalMillis: Long, requestCode: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val pendingIntent = getPendingIntent(context, requestCode.toInt())

        try {
            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + intervalMillis, // Primera ejecución
                intervalMillis, // Intervalo de repetición
                pendingIntent
            )
            Log.d("AlarmService", "Alarma inexacta programada cada $intervalMillis ms")
        } catch (e: SecurityException) {
            Log.e("AlarmService", "Error al programar alarma inexacta", e)
        }
    }

    /**
     * Cancela cualquier alarma programada.
     */
    fun cancelAlarm(context: Context, requestCode: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = getPendingIntent(context, requestCode)

        alarmManager.cancel(pendingIntent)
        Log.d("AlarmService", "Alarma cancelada")
        Toast.makeText(context, "Alarma cancelada", Toast.LENGTH_SHORT).show()
    }

    /**
     * Devuelve un PendingIntent único para el BroadcastReceiver.
     */
    private fun getPendingIntent(context: Context, requestCode: Int): PendingIntent {
        val intent = Intent(context, AlarmReceiver::class.java)
        return PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    /**
     * Verifica si la optimización de batería está bloqueando alarmas y sugiere cambiarlo.
     */
    fun checkBatteryOptimization(context: Context) {
        if (!NotificationManagerCompat.from(context).areNotificationsEnabled()) {
            Log.w("AlarmService", "Las notificaciones están desactivadas para esta app.")
            Toast.makeText(context, "Activa las notificaciones en Configuración", Toast.LENGTH_LONG).show()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val packageName = context.packageName
            if (!Settings.canDrawOverlays(context)) {
                Log.w("AlarmService", "La optimización de batería puede afectar las alarmas.")
                Toast.makeText(context, "Desactiva la optimización de batería para esta app", Toast.LENGTH_LONG).show()
            }
        }
    }
}
