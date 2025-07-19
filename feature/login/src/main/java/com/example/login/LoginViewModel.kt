package com.example.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.remote.dtos.auth.AuthDto
import com.example.data.remote.repository.auth.AuthRepository
import com.example.library.utils.hashPasswordSHA256
import com.example.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface LoginContract {
    val loginData: StateFlow<LoginData>
    val loginState: StateFlow<LoginState>
    val errorMsg: StateFlow<LoginErrorState>

    fun updateLoginField(key: String, value: String)
    fun validateEmail(): Boolean
    fun validatePassword(): Boolean
    fun isValidateData(): Boolean
    fun executeLogin()
}

data class LoginData(
    val email: String = "",
    val password: String = ""
)

data class LoginErrorState(
    val email: String = "",
    val password: String = ""
)

data class LoginState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean? = null,
    val isError: Boolean? = null
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val sessionManager: SessionManager
) : ViewModel(), LoginContract {

    private val _loginData = MutableStateFlow(LoginData())
    private val _loginState = MutableStateFlow(LoginState())
    private val _errorMsg = MutableStateFlow(LoginErrorState())

    override val loginData: StateFlow<LoginData> get() = _loginData
    override val loginState: StateFlow<LoginState> get() = _loginState
    override val errorMsg: StateFlow<LoginErrorState> get() = _errorMsg

    override fun updateLoginField(key: String, value: String) {
        val currentState = _loginData.value
        val updated = when (key) {
            "email" -> currentState.copy(email = value)
            "password" -> currentState.copy(password = value)
            else -> currentState
        }
        _loginData.value = updated
    }

    override fun validateEmail(): Boolean {
        val email = _loginData.value.email
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
        val isValid = emailRegex.matches(email)
        _errorMsg.value = _errorMsg.value.copy(
            email = if (!isValid) "Invalid email" else ""
        )
        return isValid
    }

    override fun validatePassword(): Boolean {
        val password = _loginData.value.password
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

    override fun executeLogin() {
        if (!isValidateData()) return

        viewModelScope.launch {
            _loginState.value = _loginState.value.copy(isLoading = true)

            try {
                val request = AuthDto(
                    email = _loginData.value.email,
                    encryptedPassword = hashPasswordSHA256(_loginData.value.password)
                )

                val response = authRepository.login(request)

                if (response.isSuccessful && response.body() != null) {
                    val userId = response.body()!!.id
                    val email = response.body()!!.email

                    sessionManager.setUserId(userId)
                    sessionManager.setUserEmail(email)
                    sessionManager.setLogged(true)
                    _loginState.value = _loginState.value.copy(isSuccess = true)
                } else {
                    Log.e("Login", "Error: ${response.code()} - ${response.message()}")
                }

            } catch (e: Exception) {
                Log.e("Login", "Exception: ${e.message}")
                _loginState.value = _loginState.value.copy(isError = true)
            } finally {
                _loginState.value = _loginState.value.copy(isLoading = false)
            }
        }
    }
}