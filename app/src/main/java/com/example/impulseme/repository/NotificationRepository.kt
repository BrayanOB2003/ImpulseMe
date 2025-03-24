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
                TaskInfo(
                    id = 5,
                    title = "Recordatorio 5",
                    description = "Descripción del recordatorio 5",
                    date = "2022-12-31",
                    time = "12:00",
                ),
                TaskInfo(
                    id = 6,
                    title = "Recordatorio 6",
                    description = "Descripción del recordatorio 6",
                    date = "2022-12-31",
                    time = "12:00",
                ),
                TaskInfo(
                    id = 7,
                    title = "Recordatorio 7",
                    description = "Descripción del recordatorio 7",
                    date = "2022-12-31",
                    time = "12:00",
                ),
                TaskInfo(
                    id = 8,
                    title = "Recordatorio 8",
                    description = "Descripción del recordatorio 8",
                    date = "2022-12-31",
                    time = "12:00",
                ),
                TaskInfo(
                    id = 9,
                    title = "Recordatorio 9",
                    description = "Descripción del recordatorio 9",
                    date = "2022-12-31",
                    time = "12:00",
                ),
                TaskInfo(
                    id = 10,
                    title = "Recordatorio 10",
                    description = "Descripción del recordatorio 10",
                    date = "2022-12-31",
                    time = "12:00",
                ),
            )
        )
    }
}
