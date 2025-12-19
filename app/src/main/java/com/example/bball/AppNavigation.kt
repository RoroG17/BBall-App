package com.example.bball

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bball.session.SessionManager
import com.example.bball.ui.components.layout.NavMenu
import com.example.bball.ui.components.screen.LoginScreen
import com.example.bball.viewmodels.LoginViewModel

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(loginVM = LoginViewModel(SessionManager(LocalContext.current))) }
    }
}
