package com.example.impulseme.model

data class TaskInfo(
    val id: Int,
    val title: String,
    val description: String,
    val dateStart: String,
    val dateEnd: String,
    val timeStart: String,
    val timeEnd: String,
    val priority: EnumPriorityTask,
    )
