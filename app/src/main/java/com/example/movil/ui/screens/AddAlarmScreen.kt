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
import com.example.movil.viewmodel.Alarm
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

    Scaffold(
    ) { innerPadding ->
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
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface, shape = MaterialTheme.shapes.medium),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface, // ✅ Fondo cuando está seleccionado
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface, // ✅ Fondo cuando no está seleccionado
                    disabledContainerColor = MaterialTheme.colorScheme.surface, // ✅ Fondo cuando está deshabilitado
                    cursorColor = MaterialTheme.colorScheme.primary, // ✅ Color del cursor
                    focusedTextColor = MaterialTheme.colorScheme.onSurface, // ✅ Color del texto cuando está enfocado
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant // ✅ Color del texto cuando no está enfocado
                )
            )


            Spacer(modifier = Modifier.height(16.dp))

            // **Time Picker visible en pantalla**
            TimePickerDisplay(selectedTime = time) { selectedTime -> time = selectedTime }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Descripción",
                style = MaterialTheme.typography.headlineMedium
            )

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
                    onClick = { navController.popBackStack() }, // Cancela y vuelve al Dashboard
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
fun TimePicker(selectedTime: LocalTime, onTimeSelected: (LocalTime) -> Unit) {
    val context = LocalContext.current

    Button(
        onClick = {
            val timePickerDialog = android.app.TimePickerDialog(
                context,
                { _, hourOfDay, minute ->
                    onTimeSelected(LocalTime.of(hourOfDay, minute))
                },
                selectedTime.hour,
                selectedTime.minute,
                false
            )
            timePickerDialog.show()
        }
    ) {
        Text("Hora: ${selectedTime.format(DateTimeFormatter.ofPattern("hh:mm a"))}")
    }
}

