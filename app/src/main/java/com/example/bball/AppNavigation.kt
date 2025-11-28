package com.example.bball

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bball.ui.components.layout.NavMenu
import com.example.bball.ui.components.screen.LoginScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("home") { NavMenu() }
        composable ("match/{id}" ) { backStackEntry ->
            TODO()
        }
    }
}
