package com.example.register

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class RegisterState(
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = "",
)

data class RegisterErrorState(
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = "",
)

class RegisterViewModel : ViewModel() {
    private val _register = MutableStateFlow(RegisterState())
    private val _errorMsg = MutableStateFlow(RegisterErrorState())

    val register: StateFlow<RegisterState> get() = _register
    val errorMsg: StateFlow<RegisterErrorState> get() = _errorMsg

    fun updateRegisterField(key: String, value: String) {
        val current = _register.value

        val updated = when (key) {
            "fullName" -> current.copy(fullName = value)
            "email" -> current.copy(email = value)
            "password" -> current.copy(password = value)
            "repeatPassword" -> current.copy(repeatPassword = value)
            else -> current
        }

        _register.value = updated
    }

    fun validateFullName(): Boolean {
        val fullName = _register.value.fullName.trim()

        val isValid = fullName.length >= 2

        _errorMsg.value = _errorMsg.value.copy(
            fullName = if (!isValid) "Name must be at least 2 characters" else ""
        )

        return isValid
    }

    fun validateEmail(): Boolean {
        val email = _register.value.email
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()

        val isValid = emailRegex.matches(email)

        _errorMsg.value = _errorMsg.value.copy(
            email = if (!isValid) "Invalid email" else ""
        )

        return isValid
    }

    fun validatePassword(): Boolean {
        val password = _register.value.password
        val passwordRegex = "^(?=.*\\d).{6,}$".toRegex()

        val isValid = passwordRegex.matches(password)

        _errorMsg.value = _errorMsg.value.copy(
            password = if (!isValid) "Password must be at least 6 characters and contain a number" else ""
        )

        return isValid
    }

    fun validateRepeatPassword(): Boolean {
        val isValid = _register.value.password == _register.value.repeatPassword

        _errorMsg.value = _errorMsg.value.copy(
            repeatPassword = if (!isValid) "Passwords do not match" else ""
        )

        return isValid
    }

    fun isValidateData(): Boolean {
        val isName = validateFullName()
        val isEmail = validateEmail()
        val isPass = validatePassword()
        val isConfirmPassword = validateRepeatPassword()

        return isName && isEmail && isPass && isConfirmPassword
    }
}