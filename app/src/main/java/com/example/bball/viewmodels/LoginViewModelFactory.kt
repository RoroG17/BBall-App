package com.example.bball.viewmodels
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bball.session.SessionManager

class LoginViewModelFactory(private val appContext: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            "Unknown ViewModel class: ${modelClass.name}"
        }
        val sessionManager = SessionManager(appContext)
        return LoginViewModel(sessionManager) as T
    }
}
