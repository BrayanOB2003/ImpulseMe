package com.example.impulseme.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.impulseme.model.TaskInfo
import com.example.impulseme.viewModel.TaskViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Date

@Composable
fun TaskFormDialog(taskViewModel: TaskViewModel = hiltViewModel()){
    val showForm: Boolean by taskViewModel.showTaskForm.collectAsState()

    if(showForm){
        TaskDialog(
            onConfirm = {
                //taskViewModel.addReminder(TaskInfo((0..1000).random(), title, description, "", time))
                taskViewModel.hideTaskForm()
            },
            onDismiss = { taskViewModel.hideTaskForm() }
        ){
            TaskForm()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TaskForm() {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    var showDatePicker by remember { mutableStateOf(false) }
    var startDate by remember { mutableStateOf(LocalDate.now()) }
    var endDate by remember { mutableStateOf(LocalDate.now()) }
    var dateSelected by remember { mutableStateOf("") } //START DATE - END DATE

    var showTimePicker by remember { mutableStateOf(false) }
    var startTime by remember { mutableStateOf(LocalTime.now()) }
    var endTime by remember { mutableStateOf(LocalTime.now().plusHours(1) )}
    var timeSelected by remember { mutableStateOf("") } //START TIME - END TIME

    var priority by remember { mutableStateOf("Media") }
    var reminderEnabled by remember { mutableStateOf(false) }

    fun getDateFormatted(date: LocalDate): String {
        return DateTimeFormatter.ofPattern("EEEE, dd MMM").format(date)
    }

    fun getTimeFormatted(date: LocalTime): String {
        return DateTimeFormatter.ofPattern("hh:mm a").format(date)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Título
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Título") },
            placeholder = { Text("Ejemplo: Reunión de equipo") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            leadingIcon = {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Título")
            }
        )
        // Fecha
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = getDateFormatted(startDate),
                color = Color.Gray,
                modifier = Modifier.clickable(
                    onClick = {
                        showDatePicker = true
                        dateSelected = "START DATE"
                    }
                )
            )
            Text(
                text = getTimeFormatted(startTime),
                color = Color.Gray,
                modifier = Modifier.clickable(
                    onClick = {
                        showTimePicker = true
                        timeSelected = "START TIME"

                    }
                )
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = getDateFormatted(endDate),
                color = Color.Gray,
                modifier = Modifier.clickable(
                    onClick = {
                        showDatePicker = true
                        dateSelected = "END DATE"
                    }
                )
            )
            Text(
                text = getTimeFormatted(endTime),
                color = Color.Gray,
                modifier = Modifier.clickable(
                    onClick = {
                        showTimePicker = true
                        timeSelected = "END TIME"
                    }
                )
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Prioridad")
            DropdownMenu(
                expanded = false, // Aquí puedes controlar su apertura
                onDismissRequest = {},
            ) {
                listOf("Baja", "Media", "Alta").forEach {
                    DropdownMenuItem(text = { Text(it) }, onClick = { priority = it })
                }
            }
        }
        // Recordatorio
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = reminderEnabled,
                onCheckedChange = { reminderEnabled = it }
            )
            Text("Agregar recordatorio")
        }
        // Descripción
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Descripción") },
            placeholder = { Text("Ejemplo: Discutir avances del proyecto") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 3,
            leadingIcon = {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Descripción")
            }
        )
        // Botón de Confirmación
        Button(
            onClick = { /* Acción para guardar la tarea */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text("Crear Tarea")
        }

    }
    // Selectores de Fecha y Hora
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = { },
            modifier = TODO(),
            dismissButton = TODO(),
            shape = TODO(),
            tonalElevation = TODO(),
            colors = TODO(),
            properties = TODO(),
            content = TODO()
        )
    }

    if (showTimePicker) {
        DialTimePicker(
            onConfirm = { time ->
                if(timeSelected == "START TIME"){
                    startTime = LocalTime.of(time.hour, time.minute)
                } else if (timeSelected == "END TIME"){
                    endTime = LocalTime.of(time.hour, time.minute)
                }
                showTimePicker = false
            },
            onDismiss = { showTimePicker = false }
        )
    }
}

@Composable
fun TaskDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable () -> Unit
){
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