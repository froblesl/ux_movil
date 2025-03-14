package com.example.movil.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {

    var errorMessage = mutableStateOf("")

    fun register(name: String, email: String, username: String, password: String): Boolean {
        return if (name.isNotBlank() && email.isNotBlank() && username.isNotBlank() && password.isNotBlank()) {
            errorMessage.value = "" // No hay errores
            println("Registro exitoso para: $name, $email, $username")
            true
        } else {
            errorMessage.value = "Todos los campos son obligatorios."
            false
        }
    }
}
