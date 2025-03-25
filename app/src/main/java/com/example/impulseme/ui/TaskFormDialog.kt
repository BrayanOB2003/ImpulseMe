package com.example.impulseme.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.impulseme.model.TaskInfo
import com.example.impulseme.viewModel.TaskViewModel
import com.google.android.material.textfield.TextInputEditText

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TaskForm(taskViewModel: TaskViewModel = hiltViewModel()){
    var title by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val showForm: Boolean by taskViewModel.showTaskForm.collectAsState()
    val showTimePicker by taskViewModel.showTimePicker.collectAsState()

    if(showForm){
        TaskFormDialog(
            onConfirm = {
                taskViewModel.addReminder(TaskInfo((0..1000).random(), title, description, "", time))
                taskViewModel.hideTaskForm() },
            onDismiss = { taskViewModel.hideTaskForm() }
        ){
            Column {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    placeholder = {
                        Text("Título")
                    }
                )
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    placeholder = {
                        Text("Descripción")
                    }
                )
                TextField(
                    modifier = Modifier.clickable(
                        enabled = true,
                        onClick = {
                            taskViewModel.showTimePicker()
                        }),
                    enabled = false,
                    value = time,
                    onValueChange = { title = it },
                    placeholder = { Text("Seleccionar hora") },
                    colors = TextFieldDefaults.colors().copy(
                        disabledTextColor = MaterialTheme.colorScheme.onSurface,
                        disabledIndicatorColor = MaterialTheme.colorScheme.outline,
                        disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        //For Icons
                        disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant)
                )
                DialTimePicker(
                    showTimePicker = showTimePicker,
                    onConfirm = {
                        time = "${it.hour}:${it.minute}"
                        taskViewModel.hideTimePicker()
                    },
                    onDismiss = {
                        time = ""
                        taskViewModel.hideTimePicker()
                })
            }
        }
    }
}

@Composable
fun TaskFormDialog(
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