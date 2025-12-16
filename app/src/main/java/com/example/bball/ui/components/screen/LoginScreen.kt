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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bball.R
import com.example.bball.ui.components.layout.LoadingComponent
import com.example.bball.ui.components.layout.NavMenu
import com.example.bball.viewmodels.LoginState
import com.example.bball.viewmodels.LoginViewModel

@Composable
fun LoginScreen(
    loginVM: LoginViewModel = viewModel()
) {
    when (loginVM.state) {
        is LoginState.Connect -> NavMenu()
        is LoginState.Error -> ConnectScreen(loginVM = loginVM)
        is LoginState.InitPassword -> InitPasswordScreen(loginVM = loginVM)
        LoginState.Loading -> LoadingComponent()
        LoginState.Unconnect -> ConnectScreen(loginVM = loginVM)
    }
}

@Composable
fun ConnectScreen(loginVM: LoginViewModel) {

    val state = loginVM.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(R.string.connexion_title),
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = loginVM.username,
            onValueChange = { loginVM.username = it },
            label = { stringResource(R.string.name_input) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordField(
            password = loginVM.password,
            onPasswordChange = { loginVM.password = it },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (state is LoginState.Error) {
            Text(
                text = state.message,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // Bouton Connexion
        Button(
            onClick = { loginVM.connect() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.connexion_button))
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
