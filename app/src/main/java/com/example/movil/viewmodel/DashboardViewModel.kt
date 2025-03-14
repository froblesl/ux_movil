package com.example.movil.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    // Lista mutable de alarmas con la nueva estructura
    private val _alarms = mutableStateListOf(
        Alarm(1, "Dolex - 1 Tableta", "Medicamento para aliviar el dolor muscular.", "08:30 AM", "Sonido 1")
    )

    val alarms: List<Alarm> get() = _alarms

    var searchQuery = mutableStateOf("")
    var filteredAlarms = mutableStateListOf(*_alarms.toTypedArray())

    fun onSearchQueryChanged(query: String) {
        searchQuery.value = query
        filteredAlarms.clear()

        val filteredList = if (query.isBlank()) {
            _alarms
        } else {
            _alarms.filter {
                it.title.contains(query, ignoreCase = true) ||
                        it.description.contains(query, ignoreCase = true) ||
                        it.hour.contains(query, ignoreCase = true) ||
                        it.sound.contains(query, ignoreCase = true)
            }
        }

        filteredAlarms.addAll(filteredList)
    }

    fun addAlarm(title: String, description: String, hour: String, sound: String) {
        val newId = (_alarms.maxOfOrNull { it.id } ?: 0) + 1
        val newAlarm = Alarm(newId, title, description, hour, sound)
        _alarms.add(newAlarm)
        onSearchQueryChanged(searchQuery.value) // Actualiza la búsqueda
    }

    fun removeAlarm(alarm: Alarm) {
        _alarms.remove(alarm)
        onSearchQueryChanged(searchQuery.value) // Actualiza la búsqueda
    }

    fun updateAlarm(id: Int, newTitle: String, newDescription: String, newHour: String, newSound: String) {
        val index = _alarms.indexOfFirst { it.id == id }
        if (index != -1) {
            _alarms[index] = Alarm(id, newTitle, newDescription, newHour, newSound)
            onSearchQueryChanged(searchQuery.value) // Actualizar la búsqueda
        }
    }
}
