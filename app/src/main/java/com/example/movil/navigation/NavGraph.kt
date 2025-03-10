package com.example.movil.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.movil.ui.screens.*
import com.example.movil.viewmodel.Alarm
import com.example.movil.viewmodel.DashboardViewModel


@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("dashboard") { DashboardScreen(navController) }
        composable(
            route = "alarm_info/{id}/{title}/{description}/{hour}/{sound}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType },
                navArgument("title") { type = NavType.StringType },
                navArgument("description") { type = NavType.StringType },
                navArgument("hour") { type = NavType.StringType },
                navArgument("sound") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            val title = backStackEntry.arguments?.getString("title") ?: "Sin título"
            val description = backStackEntry.arguments?.getString("description") ?: "Sin descripción"
            val hour = backStackEntry.arguments?.getString("hour") ?: "00:00"
            val sound = backStackEntry.arguments?.getString("sound") ?: "Ninguno"
            AlarmInfoScreen(navController, Alarm(id, title, description, hour, sound))
        }
        composable("add_alarm") {
            val viewModel = androidx.lifecycle.viewmodel.compose.viewModel<DashboardViewModel>()
            AddAlarmScreen(navController, viewModel)
        }





        // Agregar más pantallas aquí
    }
}