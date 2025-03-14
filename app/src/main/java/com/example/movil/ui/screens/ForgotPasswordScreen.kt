package com.example.movil.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movil.viewmodel.ForgotPasswordViewModel

@Composable
fun ForgotPasswordScreen(
    navController: NavController,
    viewModel: ForgotPasswordViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Recuperar Contraseña",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                    cursorColor = MaterialTheme.colorScheme.primary
                )
            )

            if (showError) {
                Spacer(modifier = Modifier.height(8.dp))
                Text("Ingrese un correo válido", color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (email.isNotEmpty()) {
                        viewModel.sendPasswordReset(email)
                        message = "Si el correo está registrado, recibirás un enlace de recuperación."
                        showError = false
                    } else {
                        showError = true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Enviar enlace de recuperación")
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (message.isNotEmpty()) {
                Text(text = message, color = MaterialTheme.colorScheme.primary)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botón para volver al Login
            OutlinedButton(
                onClick = { navController.navigate("login") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                )
            ) {
                Text("Volver al Inicio de Sesión")
            }
        }
    }
}


