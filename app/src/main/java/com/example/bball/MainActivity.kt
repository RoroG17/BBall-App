package com.example.bball

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bball.ui.components.screen.LoginScreen
import com.example.bball.ui.theme.BBallTheme
import com.example.bball.viewmodels.LoginViewModel
import com.example.bball.viewmodels.LoginViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BBallTheme {
                val context = LocalContext.current.applicationContext
                val loginVM: LoginViewModel = viewModel(
                    factory = LoginViewModelFactory(context)
                )
                LoginScreen(loginVM = loginVM)

            }
        }
    }
}