package com.example.movil.viewmodel

data class Alarm(
    val id: Int,
    val title: String,
    val description: String,
    val hour: String,  // Nueva hora de la alarma
    val sound: String  // Sonido seleccionado
)
