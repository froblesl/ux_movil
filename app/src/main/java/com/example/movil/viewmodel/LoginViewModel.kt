package com.example.movil.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    var username = mutableStateOf("")
    var password = mutableStateOf("")
    var isLoggedIn = mutableStateOf(false)

    fun login() {
        if (username.value == "admin" && password.value == "1234") {
            isLoggedIn.value = true
        }
    }
}
