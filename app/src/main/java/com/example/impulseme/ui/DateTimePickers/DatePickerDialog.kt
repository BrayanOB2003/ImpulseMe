package com.example.impulseme.ui.DateTimePickers

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import com.example.impulseme.utils.debugTimestamp
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialDatePicker(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val currentInstant = Calendar.getInstance()

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = currentInstant.timeInMillis,
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                debugTimestamp(datePickerState.selectedDateMillis!!)
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}