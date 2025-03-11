package com.example.movil.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SoundDropdown(selectedSound: String, onSoundSelected: (String) -> Unit) {
    val sounds = listOf("Verano", "Otoño", "Invierno", "Primavera")
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            value = selectedSound,
            onValueChange = {},
            readOnly = true,
            label = { Text("Sonido") },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(), // ✅ Corrige el problema de visualización
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                cursorColor = MaterialTheme.colorScheme.primary
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            sounds.forEach { sound ->
                DropdownMenuItem(
                    text = { Text(sound) },
                    onClick = {
                        onSoundSelected(sound)
                        expanded = false
                    }
                )
            }
        }
    }
}
