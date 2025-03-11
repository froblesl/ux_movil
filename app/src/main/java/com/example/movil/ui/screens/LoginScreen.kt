package com.example.movil.ui.screens
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movil.viewmodel.LoginViewModel

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel = viewModel()) {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
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

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Usuario") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest, // ✅ Fondo cuando está enfocado
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest, // ✅ Fondo cuando no está enfocado
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest, // ✅ Fondo cuando está deshabilitado
                    cursorColor = MaterialTheme.colorScheme.primary // ✅ Color del cursor
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest, // ✅ Fondo cuando está enfocado
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest, // ✅ Fondo cuando no está enfocado
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest, // ✅ Fondo cuando está deshabilitado
                    cursorColor = MaterialTheme.colorScheme.primary
                )
            )



            // Mensaje de error si los datos son incorrectos
            if (showError) {
                Spacer(modifier = Modifier.height(8.dp))
                Text("Usuario o contraseña incorrectos", color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botón "Iniciar Sesión"
            Button(
                onClick = {
                    if (viewModel.login(username, password)) {
                        navController.navigate("dashboard") {
                            popUpTo("login") { inclusive = true } // Evita que el usuario regrese al login
                        }
                    } else {
                        showError = true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Iniciar Sesión")
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Botón "Registrarse"
            OutlinedButton(
                onClick = { navController.navigate("register") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary, // Fondo del botón
                    contentColor = Color.White // Color del texto
                )
            ) {
                Text("Registrarse")
            }
        }
    }
}
