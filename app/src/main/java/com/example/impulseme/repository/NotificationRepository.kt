package com.example.impulseme.repository

import com.example.impulseme.model.EnumPriorityTask
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
                    title = "Revisión de código",
                    description = "Revisar el PR del módulo de autenticación",
                    dateStart = "2025-03-28",
                    dateEnd = "2025-03-28",
                    timeStart = "10:00",
                    timeEnd = "11:00",
                    priority = EnumPriorityTask.fromValueToObject("Crítica")
                ),
                TaskInfo(
                    id = 2,
                    title = "Diseño de interfaz",
                    description = "Crear wireframes para la nueva pantalla de configuración",
                    dateStart = "2025-03-29",
                    dateEnd = "2025-03-29",
                    timeStart = "14:00",
                    timeEnd = "16:00",
                    priority = EnumPriorityTask.fromValueToObject("Importante")
                ),
                TaskInfo(
                    id = 3,
                    title = "Llamada con cliente",
                    description = "Reunión con el equipo legal para discutir requisitos de la app",
                    dateStart = "2025-03-30",
                    dateEnd = "2025-03-30",
                    timeStart = "09:00",
                    timeEnd = "10:00",
                    priority = EnumPriorityTask.fromValueToObject("Neutral")
                ),
                TaskInfo(
                    id = 4,
                    title = "Escribir documentación",
                    description = "Actualizar la documentación del API en Notion",
                    dateStart = "2025-04-01",
                    dateEnd = "2025-04-01",
                    timeStart = "15:00",
                    timeEnd = "17:00",
                    priority = EnumPriorityTask.fromValueToObject("Opcional")
                )
            )
        )
    }
}
