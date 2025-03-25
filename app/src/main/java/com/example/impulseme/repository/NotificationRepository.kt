package com.example.impulseme.repository

import com.example.impulseme.model.TaskInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface ReminderRepository {
    fun getReminders(): Flow<List<TaskInfo>>
}

class FakeReminderRepository @Inject constructor() : ReminderRepository {
    override fun getReminders(): Flow<List<TaskInfo>> = flow {
        emit(
            listOf(
                TaskInfo(
                    id = 1,
                    title = "Recordatorio 1",
                    description = "Descripción del recordatorio 1",
                    date = "2022-12-31",
                    time = "12:00",
                ),
                TaskInfo(
                    id = 2,
                    title = "Recordatorio 2",
                    description = "Descripción del recordatorio 2",
                    date = "2022-12-31",
                    time = "12:00",
                ),
                TaskInfo(
                    id = 3,
                    title = "Recordatorio 3",
                    description = "Descripción del recordatorio 3",
                    date = "2022-12-31",
                    time = "12:00",
                ),
                TaskInfo(
                    id = 4,
                    title = "Recordatorio 4",
                    description = "Descripción del recordatorio 4",
                    date = "2022-12-31",
                    time = "12:00",
                ),
            )
        )
    }
}
