package com.example.impulseme.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.impulseme.R
import com.example.impulseme.model.EnumPriorityTask
import com.example.impulseme.model.TaskInfo
import com.example.impulseme.ui.dateTimePickers.DialDatePicker
import com.example.impulseme.utils.fromLocalDateToString
import com.example.impulseme.utils.fromLocalTimeToString
import com.example.impulseme.utils.fromTimestampToLocalDate
import com.example.impulseme.utils.getDateFormatted
import com.example.impulseme.utils.getTimeFormatted
import com.example.impulseme.viewModel.TaskViewModel
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun TaskFormDialog(taskViewModel: TaskViewModel = hiltViewModel()){
    val showForm: Boolean by taskViewModel.showTaskForm.collectAsState()
    var title = remember { mutableStateOf("") }
    var description = remember { mutableStateOf("") }

    var showDatePicker = remember { mutableStateOf(false) }
    var startDate = remember { mutableStateOf(LocalDate.now()) }
    var endDate = remember { mutableStateOf(LocalDate.now()) }
    var dateSelected = remember { mutableStateOf("") } //START DATE - END DATE

    var showTimePicker = remember { mutableStateOf(false) }
    var startTime = remember { mutableStateOf(LocalTime.now()) }
    var endTime = remember { mutableStateOf(LocalTime.now().plusHours(1) )}
    var timeSelected = remember { mutableStateOf("") } //START TIME - END TIME

    var priority = remember { mutableStateOf(EnumPriorityTask.NEUTRAL.value) }
    var isAllDay = remember { mutableStateOf( false ) }

    var priorityExpanded = remember { mutableStateOf(false) }

    fun canCreateTask(): Boolean {
        return title.value.isNotEmpty() && description.value.isNotEmpty()
    }

    TaskDialog(
        onConfirm = {
            if (canCreateTask()){
                taskViewModel.addReminder(TaskInfo(
                    id = (0..10000).random(),
                    title = title.value,
                    description = description.value,
                    dateStart = fromLocalDateToString(startDate.value),
                    dateEnd = fromLocalDateToString(endDate.value),
                    timeStart = fromLocalTimeToString(startTime.value),
                    timeEnd = fromLocalTimeToString(endTime.value),
                    isAllDay = isAllDay.value,
                    priority = EnumPriorityTask.fromValueToObject(priority.value)
                ))
                taskViewModel.hideTaskForm()
            }
        },
        onDismiss = { taskViewModel.hideTaskForm() }
    ){
        TaskForm(title, description,
            startDate, endDate,
            startTime, endTime,
            isAllDay, priority,
            showDatePicker, showTimePicker,
            dateSelected, timeSelected,
            priorityExpanded
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskForm(title: MutableState<String>,
             description: MutableState<String>,
             startDate: MutableState<LocalDate>,
             endDate: MutableState<LocalDate>,
             startTime: MutableState<LocalTime>,
             endTime: MutableState<LocalTime>,
             isAllDay: MutableState<Boolean>,
             priority: MutableState<String>,
             showDatePicker: MutableState<Boolean>,
             showTimePicker: MutableState<Boolean>,
             dateSelected: MutableState<String>,
             timeSelected: MutableState<String>,
             priorityExpanded: MutableState<Boolean>) {

    fun getPriorityColor(priority: String): Color {
        return EnumPriorityTask.fromValueToColor(priority)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Título
        OutlinedTextField(
            value = title.value,
            onValueChange = { title.value = it },
            label = { Text("Título") },
            placeholder = { Text("Ejemplo: Reunión de equipo") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            leadingIcon = {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Título")
            }
        )
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.schedule_24dp_1f1f1f_fill0_wght400_grad0_opsz24),
                    contentDescription = "Todo el día",
                    tint = Color.Gray,
                )
                Text(
                    text="Todo el día",
                    color = Color.Gray,
                )
            }

            Switch(
                checked = isAllDay.value,
                onCheckedChange = { isAllDay.value = it },
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        // Fecha
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = getDateFormatted(startDate.value),
                color = Color.Gray,
                modifier = Modifier.clickable(
                    onClick = {
                        showDatePicker.value = true
                        dateSelected.value = "START DATE"
                    }
                )
            )
            if(!isAllDay.value){
                Text(
                    text = getTimeFormatted(startTime.value),
                    color = Color.Gray,
                    modifier = Modifier.clickable(
                        onClick = {
                            showTimePicker.value = true
                            timeSelected.value = "START TIME"

                        }
                    )
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = getDateFormatted(endDate.value),
                color = Color.Gray,
                modifier = Modifier.clickable(
                    onClick = {
                        showDatePicker.value = true
                        dateSelected.value = "END DATE"
                    }
                )
            )
            if(!isAllDay.value){
                Text(
                    text = getTimeFormatted(endTime.value),
                    color = Color.Gray,
                    modifier = Modifier.clickable(
                        onClick = {
                            showTimePicker.value = true
                            timeSelected.value = "END TIME"
                        }
                    )
                )
            }
        }

        //Prioridad
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable(
                onClick = { priorityExpanded.value = true }
            ),
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.label_important_24dp_1f1f1f_fill1_wght400_grad0_opsz24),
                contentDescription = "Prioridad",
                tint = getPriorityColor(priority.value),
            )
            Text(
                text = priority.value,
                color = Color.Gray,
                modifier = Modifier
                    .background(Color.Transparent)
                    .padding(8.dp)
            )
            DropdownMenu(
                expanded = priorityExpanded.value,
                onDismissRequest = { priorityExpanded.value = false },
            ) {
                EnumPriorityTask.getEntries().forEach { it ->
                    DropdownMenuItem(
                        text = { Text(it) },
                        onClick = {
                            priority.value = it
                            priorityExpanded.value = false
                        })
                }
            }
        }
        // Descripción
        OutlinedTextField(
            value = description.value,
            onValueChange = { description.value = it },
            label = { Text("Descripción") },
            placeholder = { Text("Ejemplo: Discutir avances del proyecto") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 3,
            leadingIcon = {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Descripción")
            }
        )
    }

    // Selectores de Fecha y Hora
    if (showDatePicker.value) {
        DialDatePicker(
            onDateSelected = { date ->
                if(dateSelected.value == "START DATE"){
                    startDate.value = fromTimestampToLocalDate(date!!)
                } else if (dateSelected.value == "END DATE"){
                    endDate.value = fromTimestampToLocalDate(date!!)
                }
                showDatePicker.value = false
            },
            onDismiss = { showDatePicker.value = false }
        )
    }

    if (showTimePicker.value) {
        DialTimePicker(
            onConfirm = { time ->
                if(timeSelected.value == "START TIME"){
                    startTime.value = LocalTime.of(time.hour, time.minute)
                } else if (timeSelected.value == "END TIME"){
                    endTime.value = LocalTime.of(time.hour, time.minute)
                }
                showTimePicker.value = false
            },
            onDismiss = { showTimePicker.value = false }
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
        confirmButton = {
            Button(
                onClick = { onConfirm() },
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Crear Evento")
            }
        },
        text = { content() }
    )
}