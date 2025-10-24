package net.nonimi.nindotx.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import net.nonimi.nindotx.data.AuthRequest
import net.nonimi.nindotx.data.AuthResponse

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val authResponse: AuthResponse) : LoginState()
    data class Error(val message: String) : LoginState()
}

class LoginViewModel : ViewModel() {

    var state by mutableStateOf<LoginState>(LoginState.Idle)
        private set

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            state = LoginState.Loading
            try {
                val response = client.post("https://dummyjson.com/auth/login") {
                    contentType(ContentType.Application.Json)
                    setBody(AuthRequest(username, password))
                }.body<AuthResponse>()
                state = LoginState.Success(response)
            } catch (e: Exception) {
                state = LoginState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun resetState() {
        state = LoginState.Idle
    }
}