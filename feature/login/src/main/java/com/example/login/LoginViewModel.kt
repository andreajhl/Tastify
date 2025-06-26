package com.example.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class LoginState(
    val email: String = "",
    val password: String = ""
)

data class LoginErrorState(
    val email: String = "",
    val password: String = ""
)

class LoginViewModel: ViewModel() {
    private val _login = MutableStateFlow(LoginState())
    private val _errorMsg = MutableStateFlow(LoginErrorState())

    val login: StateFlow<LoginState> get() = _login
    val errorMsg: StateFlow<LoginErrorState> get() = _errorMsg

    fun updateLoginField(key: String, value: String) {
        val currentState = _login.value

        val updated = when (key) {
            "email" -> currentState.copy(email = value)
            "password" -> currentState.copy(password = value)
            else -> currentState
        }
        _login.value = updated
    }

    fun validateEmail(): Boolean {
        val email = _login.value.email
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()

        val isValid = emailRegex.matches(email)

        _errorMsg.value = _errorMsg.value.copy(
            email = if (!isValid) "Invalid email" else ""
        )

        return isValid
    }

    fun validatePassword(): Boolean {
        val password = _login.value.password
        val passwordRegex = "^(?=.*\\d).{6,}$".toRegex()

        val isValid = passwordRegex.matches(password)

        _errorMsg.value = _errorMsg.value.copy(
            password = if (!isValid) "Password must be at least 6 characters and contain a number" else ""
        )

        return isValid
    }

    fun isValidateData(): Boolean {
        val isEmail = validateEmail()
        val isPass = validatePassword()

        return  isEmail && isPass
    }
}