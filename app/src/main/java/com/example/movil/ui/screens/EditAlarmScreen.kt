package com.example.movil.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movil.viewmodel.Alarm
import com.example.movil.viewmodel.DashboardViewModel
import com.example.movil.ui.components.SoundDropdown
import com.example.movil.ui.components.TimePickerDisplay
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditAlarmScreen(navController: NavController, viewModel: DashboardViewModel, alarmId: Int) {
    val context = LocalContext.current

    // Buscar la alarma en la lista de alarmas
    val alarm = viewModel.alarms.find { it.id == alarmId } ?: return

    var alarmTitle by remember { mutableStateOf(alarm.title) }
    var description by remember { mutableStateOf(alarm.description) }
    var selectedSound by remember { mutableStateOf(alarm.sound) }
    var time by remember { mutableStateOf(LocalTime.parse(alarm.hour, DateTimeFormatter.ofPattern("hh:mm a"))) }

    val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a")

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Nombre de la alarma
            OutlinedTextField(
                value = alarmTitle,
                onValueChange = { alarmTitle = it },
                label = { Text("Nombre de la alarma") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // **Time Picker visible en pantalla**
            TimePickerDisplay(selectedTime = time) { selectedTime -> time = selectedTime }

            Spacer(modifier = Modifier.height(16.dp))

            // Descripción
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Selector de sonido
            SoundDropdown(selectedSound) { selectedSound = it }

            Spacer(modifier = Modifier.height(32.dp))

            // Botones de acción
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = { navController.popBackStack() }, // Cancela y vuelve sin guardar
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancelar")
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = {
                        if (alarmTitle.isNotBlank() && description.isNotBlank()) {
                            // Actualizar la alarma en el ViewModel
                            viewModel.updateAlarm(alarmId, alarmTitle, description, time.format(timeFormatter), selectedSound)
                            Toast.makeText(context, "Alarma actualizada", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        } else {
                            Toast.makeText(context, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Guardar")
                }
            }
        }
    }
}
