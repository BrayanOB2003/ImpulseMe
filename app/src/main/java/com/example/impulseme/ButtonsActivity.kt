package com.example.impulseme

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.impulseme.ui.theme.ImpulseMeTheme
import com.example.impulseme.services.NotificationBuildServices
import com.example.impulseme.services.ScheduleAlarmService
import com.example.impulseme.services.alarm.AlarmReceiver
import java.util.Calendar

class ButtonsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ImpulseMeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val context: Context = LocalContext.current
    var selectedTime: TimePickerState? by remember { mutableStateOf(null) }
    var showDialExample by remember { mutableStateOf(false) }
    var id = 0
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = modifier.fillMaxSize()) {
        Column {
            CreateButtons(
                listOf(
                    "Notificación Sencilla" to {
                        showDialExample = true
                    },
                    "Notificación con imagen" to {
                        NotificationBuildServices(context, "Imagen")
                            .createImageNotification(
                                2,
                                "Notificación con imagen",
                                "Notificación de prueba con imagen...",
                                Bitmap.createBitmap(100, 50, Bitmap.Config.ARGB_8888).apply { eraseColor(
                                    Color.BLUE) }
                            )
                    },
                    "Notificación con texto largo" to {
                        NotificationBuildServices(context, "TextoLargo")
                            .createLargeTextNotification(
                                3,
                                "Notificación con texto largo",
                                "Notificación de prueba con texto largo...".repeat(10)
                            )
                    },
                )
            )
            if(showDialExample) {
                DialTimePicker(
                    onConfirm = {
                        timePickerState -> selectedTime = timePickerState
                        ScheduleAlarmService().scheduleExactAlarm(context, selectedTime!!.hour, selectedTime!!.minute, id++)
                        showDialExample = false
                    },
                    onDismiss = { showDialExample = false }
                )
            }
        }
    }


    if (selectedTime != null) {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, selectedTime!!.hour)
        cal.set(Calendar.MINUTE, selectedTime!!.minute)
        cal.isLenient = false
        Toast.makeText(context, "Alarma programada para las ${selectedTime!!.hour}:${selectedTime!!.minute}", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun CreateButtons(buttons: List<Pair<String,  () -> Unit>>)
{
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        buttons.forEach() { (text, onClick) ->
            Button(onClick = onClick) {
                Text(text)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialTimePicker(
    onConfirm: (TimePickerState) -> Unit,
    onDismiss: () -> Unit,
) {
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    Column {
        TimePickerDialog(onDismiss = { onDismiss() }, onConfirm = { onConfirm(timePickerState) }) {
            TimePicker(
                state = timePickerState,
            )
        }
    }
}

@Composable
fun TimePickerDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Dismiss")
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text("OK")
            }
        },
        text = { content() }
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ImpulseMeTheme {
        Greeting("Android")
    }
}