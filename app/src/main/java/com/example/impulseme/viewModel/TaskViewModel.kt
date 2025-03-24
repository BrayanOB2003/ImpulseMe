package com.example.impulseme.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.impulseme.model.TaskInfo
import com.example.impulseme.repository.ReminderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: ReminderRepository
)  : ViewModel() {

    private val _reminders: MutableStateFlow<List<TaskInfo>> = MutableStateFlow(emptyList())
    val reminders: StateFlow<List<TaskInfo>> = _reminders.asStateFlow()

    init {
        getReminders()
    }

    private fun getReminders() {
        viewModelScope.launch {
            val response = repository.getReminders().single()
            _reminders.value = response
        }
    }

    fun deleteReminder(id: Int) {
        viewModelScope.launch {
            _reminders.update { currentList ->
                currentList.toMutableList().apply { removeAll { it.id == id } }
            }
        }
    }
}