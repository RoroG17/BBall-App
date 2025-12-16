package com.example.bball.ui.components.screen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.bball.viewmodels.LoginState
import com.example.bball.viewmodels.LoginViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    loginVM: LoginViewModel = viewModel()
) {
    when (loginVM.state) {
        is LoginState.Connect -> navController.navigate("home_screen/${(loginVM.state as LoginState.Connect).user?.joueur}")
        is LoginState.Error -> Log.e("Login error", (loginVM.state as LoginState.Error).message)
        is LoginState.InitPassword -> InitPasswordScreen(loginVM = loginVM)
        LoginState.Loading -> ConnectScreen(loginVM = loginVM)
    }
}

@Composable
fun ConnectScreen(loginVM: LoginViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Connexion",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Champ Username
        OutlinedTextField(
            value = loginVM.username,
            onValueChange = { loginVM.username = it },
            label = { Text("Nom") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Champ Mot de passe
        PasswordField(
            password = loginVM.password,
            onPasswordChange = { loginVM.password = it },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Bouton Connexion
        Button(
            onClick = { loginVM.connect() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Se connecter")
        }
    }
}

@Composable
fun InitPasswordScreen(
    modifier : Modifier = Modifier,
    loginVM : LoginViewModel
    ) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Initialisation du compte",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        PasswordField(
            password = loginVM.password,
            onPasswordChange = { loginVM.password = it },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Champ Mot de passe
        PasswordField(
            password = loginVM.passwordVerify,
            onPasswordChange = { loginVM.passwordVerify = it },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Bouton Connexion
        Button(
            onClick = {
                if (loginVM.password == loginVM.passwordVerify) {
                    loginVM.InitAccount()
                } else {
                    Log.d("Verify password","Les mots de passe ne correspondent pas")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Se connecter")
        }
    }
}

@Composable
fun PasswordField(
    password: String,
    onPasswordChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text("Mot de passe") },
        singleLine = true,
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                Icon(
                    imageVector = if (isPasswordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                    contentDescription = if (isPasswordVisible) "Masquer le mot de passe" else "Afficher le mot de passe"
                )
            }
        },
        modifier = modifier
    )
}
