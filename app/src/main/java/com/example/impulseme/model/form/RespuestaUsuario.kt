package com.example.impulseme.model.form

data class RespuestaUsuario(
    val cuestionarioId: String = "",
    val usuarioId: String? = null,
    val respondidoEn: String = "",
    val puntajeTotal: Int = 0,
    val respuestas: Map<String, Any> = emptyMap()
)