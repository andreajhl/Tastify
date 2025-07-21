package com.example.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.library.utils.isValidEmail
import com.example.library.utils.isValidPassword
import com.example.library.utils.isValidText
import com.example.useCase.login.LoginUseCase
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
    val email: Boolean? = null,
    val password: Boolean? = null,
)

data class LoginState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean? = null,
    val isError: Boolean? = null
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
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
        val isValid = isValidEmail(_loginData.value.email)

        _errorMsg.value = _errorMsg.value.copy(email = !isValid)

        return isValid
    }

    override fun validatePassword(): Boolean {
        val isValid = isValidPassword(_loginData.value.password)

        _errorMsg.value = _errorMsg.value.copy(password = !isValid)

        return isValid
    }

    override fun isValidateData(): Boolean {
        return validateEmail() && validatePassword()
    }

    override fun executeLogin() {
        if (!isValidateData()) return

        viewModelScope.launch {
            _loginState.value = _loginState.value.copy(isLoading = true)

            val result = loginUseCase(
                email = _loginData.value.email,
                password = _loginData.value.password
            )

            if (result.isSuccess) {
                _loginState.value = _loginState.value.copy(isSuccess = true)
            } else {
                _loginState.value = _loginState.value.copy(isError = true)
            }

            _loginState.value = _loginState.value.copy(isLoading = false)
        }
    }
}