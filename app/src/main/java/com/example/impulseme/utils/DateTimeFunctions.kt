package com.example.impulseme.utils

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun getDateFormatted(date: LocalDate): String {
    return DateTimeFormatter.ofPattern("EEEE, dd MMM").format(date)
}

fun getTimeFormatted(date: LocalTime): String {
    return DateTimeFormatter.ofPattern("hh:mm a").format(date)
}

fun fromLocalDateToString(date: LocalDate): String {
    return date.format(DateTimeFormatter.ofPattern("EEEE, dd MMM"))
}

fun fromLocalTimeToString(time: LocalTime): String {
    return time.format(DateTimeFormatter.ofPattern("hh:mm a"))
}