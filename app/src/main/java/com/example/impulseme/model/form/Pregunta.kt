package com.example.impulseme.model.form

data class Pregunta(
    val orden: Int = 0,
    val etiqueta: String = "",
    val tipo: String = "",
    val requerido: Boolean = false,
    val opciones: List<Opcion>? = null,
    val rangos: List<RangoPuntaje>? = null
)