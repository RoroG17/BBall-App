package com.example.bball.ui.components.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bball.R
import com.example.bball.ui.components.layout.LoadingComponent
import com.example.bball.ui.components.layout.NavMenu
import com.example.bball.ui.components.layout.SectionTitle
import com.example.bball.viewmodels.LoginState
import com.example.bball.viewmodels.LoginViewModel

@Composable
fun LoginScreen(
    loginVM: LoginViewModel = viewModel()
) {
    when (loginVM.state) {
        is LoginState.Connect -> NavMenu(
            userViewModel = loginVM
        )
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
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        SectionTitle(text = stringResource(R.string.connexion_title))

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = loginVM.username,
            onValueChange = { loginVM.username = it },
            label = { Text(stringResource(R.string.name_input)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                autoCorrect = false
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordField(
            password = loginVM.password,
            onPasswordChange = { loginVM.password = it },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (state is LoginState.Error) {
            Text(
                text = state.message,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { loginVM.connect() },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(stringResource(R.string.connexion_button))
        }
    }
}

@Composable
fun InitPasswordScreen(
    modifier: Modifier = Modifier,
    loginVM: LoginViewModel
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        SectionTitle(text = stringResource(R.string.title_init_account))

        Spacer(modifier = Modifier.height(32.dp))

        PasswordField(
            password = loginVM.password,
            onPasswordChange = { loginVM.password = it },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordField(
            password = loginVM.passwordVerify,
            onPasswordChange = { loginVM.passwordVerify = it },
            label = "Confirmer le mot de passe",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = loginVM.errorMessage,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp)
        )

        Button(
            onClick = {
                if (loginVM.password == loginVM.passwordVerify) {
                    loginVM.initAccount()
                } else {
                    loginVM.errorMessage = "Les mots de passe ne correspondent pas"
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(stringResource(R.string.label_agree))
        }
    }
}


@Composable
fun PasswordField(
    password: String,
    onPasswordChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Mot de passe"
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text(label) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            autoCorrect = false
        ),
        visualTransformation = if (isPasswordVisible)
            VisualTransformation.None
        else
            PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                Icon(
                    imageVector = if (isPasswordVisible)
                        Icons.Filled.VisibilityOff
                    else
                        Icons.Filled.Visibility,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        modifier = modifier
    )
}
