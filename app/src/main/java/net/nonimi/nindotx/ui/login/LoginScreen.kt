package net.nonimi.nindotx.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.nonimi.nindotx.BuildConfig
import net.nonimi.nindotx.R

@Composable
fun LoginScreen(onLoginClicked: (String) -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf<String?>(null) }
    val loginViewModel: LoginViewModel = viewModel()

    val credentialsMissingError = stringResource(R.string.login_error_credentials_missing)
    val loginAuthFailedError = stringResource(R.string.login_error_auth_failed)

    val state = loginViewModel.state

    if (showErrorDialog != null) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = null },
            title = { Text(stringResource(R.string.login_error_title)) },
            text = { Text(showErrorDialog!!) },
            confirmButton = {
                TextButton(onClick = { showErrorDialog = null }) {
                    Text(stringResource(R.string.dialog_ok))
                }
            }
        )
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (state is LoginState.Loading) {
            CircularProgressIndicator()
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.app_title),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(25.dp))
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text(stringResource(R.string.prompt_username)) }
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(stringResource(R.string.prompt_password)) },
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = rememberMe,
                        onCheckedChange = { rememberMe = it }
                    )
                    Text(stringResource(R.string.action_remember_me))
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    if (username.isEmpty() || password.isEmpty()) {
                        showErrorDialog = credentialsMissingError
                    } else {
                        loginViewModel.login(username, password)
                    }
                }) {
                    Text(stringResource(R.string.action_login))
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = stringResource(R.string.info_version, BuildConfig.VERSION_NAME))
                Text(text = stringResource(R.string.info_build_time, BuildConfig.BUILD_TIME))
            }
        }
    }

    when (state) {
        is LoginState.Success -> {
            LaunchedEffect(state) {
                onLoginClicked(state.authResponse.username)
            }
        }
        is LoginState.Error -> {
            LaunchedEffect(state) {
                showErrorDialog = String.format(loginAuthFailedError, state.message)
                loginViewModel.resetState() // Reset state
            }
        }
        else -> {}
    }
}
