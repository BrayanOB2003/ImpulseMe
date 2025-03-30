package com.example.impulseme.utils

import android.icu.util.TimeZone
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
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

fun fromTimestampToLocalDate(timestamp: Long, timeZone: ZoneId = ZoneId.systemDefault()): LocalDate {
    return LocalDate.ofEpochDay(timestamp / 86_400_000) // Convertir directamente desde días
}

fun fromLocalDateToTimestamp(date: LocalDate): Long {
    val timeZone = date.atStartOfDay(ZoneId.systemDefault())
    val instant = timeZone.toInstant()
    return instant.toEpochMilli()
}

fun debugTimestamp(timestamp: Long, timeZone: ZoneId = ZoneId.systemDefault()) {
    val instant = Instant.ofEpochMilli(timestamp)
    val zonedDateTime = instant.atZone(timeZone)
    val localDate = zonedDateTime.toLocalDate()

    println("Timestamp: $timestamp")
    println("Instant (UTC): $instant")
    println("ZonedDateTime ($timeZone): $zonedDateTime")
    println("Final LocalDate: $localDate")
}