package com.example.bball.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bball.models.User
import com.example.bball.network.LoginAPI
import com.example.bball.network.LoginRequest
import com.example.bball.session.SessionManager
import kotlinx.coroutines.launch

sealed interface LoginState {
    data class Error(val message: String) : LoginState
    data class InitPassword(val username: String) : LoginState
    data class Connect(val user: User?) : LoginState
    object Unconnect : LoginState
    object Loading : LoginState
}

class LoginViewModel(
    private val sessionManager: SessionManager
) : ViewModel() {

    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var passwordVerify by mutableStateOf("")

    var errorMessage by mutableStateOf("")

    var state: LoginState by mutableStateOf(LoginState.Unconnect)
        private set

    init {
        sessionManager.getUser()?.let { user ->
            state = LoginState.Connect(user)
        }
    }

    fun connect() {

        if (username.isBlank()) {
            state = LoginState.Error("Veuillez entrer un username")
            return
        }

        state = LoginState.Loading

        viewModelScope.launch {
            try {
                val response = LoginAPI.retrofitService.connect(
                    LoginRequest(
                        username = username,
                        password = password.ifBlank { null }
                    )
                )

                state = when (response.code()) {
                    201 -> LoginState.InitPassword(username)

                    200 -> {
                        val user = response.body()?.user
                        if (user != null) {
                            sessionManager.saveUser(user)
                            LoginState.Connect(user)
                        } else {
                            LoginState.Error("Utilisateur invalide")
                        }
                    }

                    400 -> LoginState.Error("Veuillez entrer un mot de passe")
                    401 -> LoginState.Error("Mot de passe incorrect")
                    404 -> LoginState.Error("Utilisateur non trouvé")

                    else -> {
                        val message = response.body()?.message
                            ?: "Erreur inconnue ${response.code()}"
                        LoginState.Error(message)
                    }
                }

            } catch (e: Exception) {
                state = LoginState.Error("Erreur réseau : ${e.localizedMessage}")
            }
        }
    }

    fun initAccount() {
        if (password.isBlank() || password != passwordVerify) {
            state = LoginState.Error("Erreur de mot de passe")
            return
        }

        state = LoginState.Loading

        viewModelScope.launch {
            try {
                val response = LoginAPI.retrofitService.initAccount(
                    LoginRequest(username = username, password = password)
                )

                state = when (response.code()) {
                    200 -> {
                        val user = response.body()?.user
                        if (user != null) {
                            sessionManager.saveUser(user)
                            LoginState.Connect(user)
                        } else {
                            LoginState.Error("Utilisateur invalide")
                        }
                    }

                    404 -> LoginState.Error("Utilisateur non trouvé")

                    else -> {
                        val message = response.body()?.message ?: "Erreur inconnue"
                        LoginState.Error(message)
                    }
                }

            } catch (e: Exception) {
                state = LoginState.Error("Erreur réseau : ${e.localizedMessage}")
            }
        }
    }

    fun logout() {
        sessionManager.logout()
        state = LoginState.Unconnect
    }
}

