package com.example.impulseme.model

import androidx.compose.ui.graphics.Color

enum class EnumPriorityTask(val value: String, val color: Color) {
    CRITICAL("Crítica", Color.Red),
    IMPORTANT("Importante", Color.Red),
    NEUTRAL("Neutral", Color.Yellow),
    OPTIONAL("Opcional", Color.Gray);

    companion object {
        fun getEntries(): List<String> {
            return listOf(
                CRITICAL.value,
                IMPORTANT.value,
                NEUTRAL.value,
                OPTIONAL.value
            )
        }

        fun fromValueToColor(value: String): Color {
            return when (value) {
                CRITICAL.value -> CRITICAL.color
                IMPORTANT.value -> IMPORTANT.color
                NEUTRAL.value -> NEUTRAL.color
                OPTIONAL.value -> OPTIONAL.color
                else -> Color.Transparent
            }
        }

        fun fromValueToObject(value: String): EnumPriorityTask {
            return when (value) {
                CRITICAL.value -> CRITICAL
                IMPORTANT.value -> IMPORTANT
                NEUTRAL.value -> NEUTRAL
                OPTIONAL.value -> OPTIONAL
                else -> OPTIONAL
            }
        }
    }

}