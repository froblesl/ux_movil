package com.example.movil.ui.screens

import com.example.movil.ui.components.SoundDropdown
import com.example.movil.ui.components.TimePickerDisplay
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movil.viewmodel.DashboardViewModel
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAlarmScreen(navController: NavController, viewModel: DashboardViewModel) {
    val context = LocalContext.current

    var alarmTitle by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedSound by remember { mutableStateOf("Verano") }
    var time by remember { mutableStateOf(LocalTime.now()) }

    val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a")

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .navigationBarsPadding(), // 🔹 Evita que los botones se oculten
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Nombre de la alarma
                OutlinedTextField(
                    value = alarmTitle,
                    onValueChange = { alarmTitle = it },
                    label = { Text("Nombre de la alarma") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface, shape = MaterialTheme.shapes.medium),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        disabledContainerColor = MaterialTheme.colorScheme.surface,
                        cursorColor = MaterialTheme.colorScheme.primary,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Selector de días de la semana
                DaysOfWeekSelector()

                Spacer(modifier = Modifier.height(16.dp))

                // Time Picker
                TimePickerDisplay(selectedTime = time) { selectedTime -> time = selectedTime }

                Spacer(modifier = Modifier.height(16.dp))

                // Descripción
                Text(text = "Descripción", style = MaterialTheme.typography.headlineMedium)

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(1.dp))

                // Selector de sonido
                SoundDropdown(selectedSound) { selectedSound = it }
            }

            Spacer(modifier = Modifier.height(16.dp)) // 🔹 Espacio extra antes de los botones

            // Botones de acción
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancelar")
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = {
                        if (alarmTitle.isNotBlank() && description.isNotBlank()) {
                            viewModel.addAlarm(alarmTitle, description, time.format(timeFormatter), selectedSound)
                            Toast.makeText(context, "Alarma añadida", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        } else {
                            Toast.makeText(context, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Agregar")
                }
            }
        }
    }
}

@Composable
fun DaysOfWeekSelector() {
    val days = listOf("L", "M", "X", "J", "V", "S", "D")
    val selectedDays = remember { mutableStateListOf(*List(7) { false }.toTypedArray()) }

    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
        days.forEachIndexed { index, day ->
            val isSelected = selectedDays[index]

            TextButton(
                onClick = { selectedDays[index] = !selectedDays[index] },
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                        else MaterialTheme.colorScheme.surface,
                        shape = MaterialTheme.shapes.medium
                    )
            ) {
                Text(day, color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface)
            }

            Spacer(modifier = Modifier.width(4.dp))
        }
    }
}
