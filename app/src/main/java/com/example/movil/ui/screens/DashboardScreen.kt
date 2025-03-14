package com.example.movil.ui.screens
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movil.viewmodel.DashboardViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import com.example.movil.R
import com.example.movil.viewmodel.Alarm
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold


@Composable
fun DashboardScreen(navController: NavController, viewModel: DashboardViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {

    val searchQuery = viewModel.searchQuery.value
    val alarms = viewModel.filteredAlarms.toList()

    Scaffold(
        topBar = {
            Column { // ✅ Agregamos un espacio antes del TopAppBar
                Spacer(modifier = Modifier.height(24.dp)) // Espaciado antes del TopBar
                DashboardTopBar(
                    searchQuery = searchQuery,
                    onSearchChange = { viewModel.onSearchQueryChanged(it) }
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
    Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Alarmas",
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(16.dp))

                if (alarms.isNotEmpty()) {
                    LazyColumn {
                        items(alarms.size) { index ->
                            AlarmCard(alarm = alarms[index], navController, viewModel)
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                } else {
                    Text(
                        text = "No hay alarmas disponibles",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }

            FloatingActionButton(
                onClick = { navController.navigate("add_alarm") },
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier
                    .align(Alignment.BottomCenter) // Centrado en la parte inferior
                    .padding(bottom = 32.dp) // Ajustar el espaciado
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar Alarma")
            }
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardTopBar(searchQuery: String, onSearchChange: (String) -> Unit) {
    TopAppBar(
        title = {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchChange,
                placeholder = { Text("Buscar alarmas...") },
                singleLine = true,
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Buscar")
                },
                modifier = Modifier.fillMaxWidth(0.8f)
            )
        },
        actions = {
            Image(
                painter = painterResource(id = R.drawable.ic_avatar_placeholder),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
        }
    )
}

@Composable
fun AlarmCard(alarm: Alarm, navController: NavController, viewModel: DashboardViewModel) {
    var isAlarmEnabled by remember { mutableStateOf(true) } // Estado local del switch

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("alarm_info/${alarm.id}/${alarm.title}/${alarm.description}/${alarm.hour}/${alarm.sound}")
            },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = alarm.title, style = MaterialTheme.typography.bodyLarge)
                Text(text = alarm.hour, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }

            // ✅ Switch para activar/desactivar alarma
            Switch(
                checked = isAlarmEnabled,
                onCheckedChange = { isAlarmEnabled = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                    uncheckedThumbColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}







