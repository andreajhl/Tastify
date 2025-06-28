package com.example.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface LoginContract {
    val login: StateFlow<LoginState>
    val errorMsg: StateFlow<LoginErrorState>

    fun updateLoginField(key: String, value: String)
    fun validateEmail(): Boolean
    fun validatePassword(): Boolean
    fun isValidateData(): Boolean
}

data class LoginState(
    val email: String = "",
    val password: String = ""
)

data class LoginErrorState(
    val email: String = "",
    val password: String = ""
)

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel(), LoginContract {

    private val _login = MutableStateFlow(LoginState())
    private val _errorMsg = MutableStateFlow(LoginErrorState())

    override val login: StateFlow<LoginState> get() = _login
    override val errorMsg: StateFlow<LoginErrorState> get() = _errorMsg

    override fun updateLoginField(key: String, value: String) {
        val currentState = _login.value
        val updated = when (key) {
            "email" -> currentState.copy(email = value)
            "password" -> currentState.copy(password = value)
            else -> currentState
        }
        _login.value = updated
    }

    override fun validateEmail(): Boolean {
        val email = _login.value.email
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
        val isValid = emailRegex.matches(email)
        _errorMsg.value = _errorMsg.value.copy(
            email = if (!isValid) "Invalid email" else ""
        )
        return isValid
    }

    override fun validatePassword(): Boolean {
        val password = _login.value.password
        val passwordRegex = "^(?=.*\\d).{6,}$".toRegex()
        val isValid = passwordRegex.matches(password)
        _errorMsg.value = _errorMsg.value.copy(
            password = if (!isValid) "Password must be at least 6 characters and contain a number" else ""
        )
        return isValid
    }

    override fun isValidateData(): Boolean {
        return validateEmail() && validatePassword()
    }
}