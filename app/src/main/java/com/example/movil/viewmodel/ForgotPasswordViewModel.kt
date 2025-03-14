package com.example.movil.viewmodel

import androidx.lifecycle.ViewModel

class ForgotPasswordViewModel : ViewModel() {

    fun sendPasswordReset(email: String): Boolean {
        // Aquí podrías agregar validaciones básicas (ejemplo: que el email contenga '@')
        return email.contains("@")
    }
}
