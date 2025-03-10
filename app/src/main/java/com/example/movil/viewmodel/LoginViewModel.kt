package com.example.movil.viewmodel

import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    private val validUser = "admin"
    private val validPassword = "1234"

    fun login(username: String, password: String): Boolean {
        return username == validUser && password == validPassword
    }
}
