package com.example.bball

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalContext
import com.example.bball.session.SessionManager
import com.example.bball.ui.components.screen.LoginScreen
import com.example.bball.ui.theme.BBallTheme
import com.example.bball.viewmodels.LoginViewModel

class MainActivity : ComponentActivity() {
    @SuppressLint("ViewModelConstructorInComposable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BBallTheme {
                LoginScreen(loginVM = LoginViewModel(SessionManager(LocalContext.current)))
            }
        }
    }
}