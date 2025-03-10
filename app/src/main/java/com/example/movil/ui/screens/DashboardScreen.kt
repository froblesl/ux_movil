package com.example.movil.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movil.R
import com.example.movil.viewmodel.DashboardViewModel

@Composable
fun DashboardScreen(navController: NavController, viewModel: DashboardViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {

    val searchQuery = viewModel.searchQuery.value
    val alarms = viewModel.filteredAlarms.value

    Scaffold(
        topBar = {
            DashboardTopBar(
                searchQuery = searchQuery,
                onSearchChange = { viewModel.onSearchQueryChanged(it) }
            )
        },
        containerColor = MaterialTheme.colorScheme.background // Asegura que no haya fondo morado
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
                    AlarmCard(title = alarms[0].title, description = alarms[0].description)
                } else {
                    Text("No hay alarmas disponibles", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                }
            }

            // FAB centrado en la parte inferior
            FloatingActionButton(
                onClick = { /* Aquí irá la navegación al agregar alarma */ },
                containerColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .align(Alignment.BottomCenter) // Centrado en la parte inferior
                    .padding(bottom = 32.dp) // Ajustar la separación
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
fun AlarmCard(title: String, description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Aquí irá la navegación a detalles de la alarma */ },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_avatar_placeholder),
                contentDescription = "Icono de alarma",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = title, style = MaterialTheme.typography.bodyLarge)
                Text(text = description, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
        }
    }
}



