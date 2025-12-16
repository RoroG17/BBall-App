package com.example.bball.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.bball.network.LoginAPI
import com.example.bball.network.LoginRequest
import androidx.lifecycle.viewModelScope
import com.example.bball.models.User
import kotlinx.coroutines.launch

sealed interface LoginState {
    data class Error(val message: String) : LoginState
    data class InitPassword(val username: String) : LoginState
    data class Connect(val user: User?) : LoginState
    object Loading : LoginState
}

class LoginViewModel : ViewModel() {

    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var passwordVerify by mutableStateOf("")
    var state: LoginState by mutableStateOf(LoginState.Loading)
        private set

    fun connect() {
        Log.d("Api Login", "Bouton click")
        if (username.isBlank()) {
            state = LoginState.Error("Veuillez entrer un username")
            return
        }

        state = LoginState.Loading

        viewModelScope.launch {
            try {
                val response = LoginAPI.retrofitService.connect(
                    LoginRequest(username = username, password = password.ifBlank { null })
                )

                state = when (response.code()) {
                    201 -> LoginState.InitPassword(username)   // mot de passe à initialiser
                    200 -> LoginState.Connect(response.body()?.user)        // connexion réussie
                    401 -> LoginState.Error("Mot de passe incorrect")
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

    fun InitAccount() {
        Log.d("Api Login", "Initialisation du compte")

        if (password.isBlank() || password != passwordVerify) {
            state = LoginState.Error("Erreur de mot de passe")
            return
        }

        viewModelScope.launch {
            try {
                val response = LoginAPI.retrofitService.initAccount(
                    LoginRequest(username = username, password = password)
                )

                if (response.code() == 200) {
                    Log.d("Api Login", "Compte créé ")
                }
                state = when (response.code()) {
                    200 -> LoginState.Connect(response.body()?.user)
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
}
