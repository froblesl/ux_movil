package com.example.movil.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    private val allAlarms = listOf(
        Alarm("Dolex - 1 tableta", "10:30 am"),
    )

    var filteredAlarms = mutableStateOf(allAlarms)
    var searchQuery = mutableStateOf("")

    fun onSearchQueryChanged(query: String) {
        searchQuery.value = query
        filteredAlarms.value = if (query.isBlank()) {
            allAlarms
        } else {
            allAlarms.filter {
                it.title.contains(query, ignoreCase = true) || it.description.contains(query, ignoreCase = true)
            }
        }
    }
}

data class Alarm(val title: String, val description: String)
