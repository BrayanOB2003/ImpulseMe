package com.example.impulseme.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.material.textfield.TextInputEditText

@Preview
@Composable
fun TaskForm(){
    var title by remember { mutableStateOf("") }

    TaskFormDialog(onConfirm = {}, onDismiss = {}){
        Column {
            TextField(
                value = title,
                onValueChange = { title = it },
                placeholder = {
                    Text("Título")
                }
            )
            TextField(
                value = title,
                onValueChange = { title = it },
                placeholder = {
                    Text("Descripción")
                }
            )
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