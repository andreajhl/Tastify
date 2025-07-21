package com.example.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.library.utils.hashPasswordSHA256
import com.example.library.utils.isEquals
import com.example.library.utils.isValidEmail
import com.example.library.utils.isValidPassword
import com.example.library.utils.isValidText
import com.example.remotes.dtos.auth.RegisterDto
import com.example.useCase.register.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface RegisterContract {
    val registerState: StateFlow<RegisterState>
    val registerData: StateFlow<RegisterDataState>
    val errorMsg: StateFlow<RegisterDataErrorState>

    fun updateRegisterField(key: String, value: String)
    fun validateName(): Boolean
    fun validateLastName(): Boolean
    fun validateEmail(): Boolean
    fun validatePassword(): Boolean
    fun validateRepeatPassword(): Boolean
    fun isValidateData(): Boolean
    fun executeRegister()
}

data class RegisterDataState(
    val name: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = "",
)

data class RegisterState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean? = null,
    val isError: Boolean? = null
)

data class RegisterDataErrorState(
    val name: Boolean? = null,
    val lastName: Boolean? = null,
    val email: Boolean? = null,
    val password: Boolean? = null,
    val repeatPassword: Boolean? = null
)

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel(), RegisterContract {
    private val _registerState = MutableStateFlow(RegisterState())
    private val _registerData = MutableStateFlow(RegisterDataState())
    private val _errorMsg = MutableStateFlow(RegisterDataErrorState())

    override val registerState: StateFlow<RegisterState> get() = _registerState
    override val registerData: StateFlow<RegisterDataState> get() = _registerData
    override val errorMsg: StateFlow<RegisterDataErrorState> get() = _errorMsg

    override fun updateRegisterField(key: String, value: String) {
        val current = _registerData.value
        val updated = when (key) {
            "name" -> current.copy(name = value)
            "lastName" -> current.copy(lastName = value)
            "email" -> current.copy(email = value)
            "password" -> current.copy(password = value)
            "repeatPassword" -> current.copy(repeatPassword = value)
            else -> current
        }
        _registerData.value = updated
    }

    override fun validateName(): Boolean {
        val isValid = isValidText(_registerData.value.name)

        _errorMsg.value = _errorMsg.value.copy(name = !isValid)

        return isValid
    }

    override fun validateLastName(): Boolean {
        val isValid = isValidText(_registerData.value.lastName)

        _errorMsg.value = _errorMsg.value.copy(lastName = !isValid)

        return isValid
    }

    override fun validateEmail(): Boolean {
        val isValid = isValidEmail(_registerData.value.email)

        _errorMsg.value = _errorMsg.value.copy(email = !isValid)

        return isValid
    }

    override fun validatePassword(): Boolean {
        val isValid = isValidPassword(_registerData.value.password)

        _errorMsg.value = _errorMsg.value.copy(password = !isValid)

        return isValid
    }

    override fun validateRepeatPassword(): Boolean {
        val isValid = isEquals(_registerData.value.password, _registerData.value.repeatPassword)

        _errorMsg.value = _errorMsg.value.copy(repeatPassword = !isValid)

        return isValid
    }

    override fun isValidateData(): Boolean {
        return validateName() &&
                validateLastName() &&
                validateEmail() &&
                validatePassword() &&
                validateRepeatPassword()
    }

    override fun executeRegister() {
        if (!isValidateData()) return

        viewModelScope.launch {
            _registerState.value = _registerState.value.copy(isLoading = true)

            val request = RegisterDto(
                name = _registerData.value.name,
                lastName = _registerData.value.lastName,
                email = _registerData.value.email,
                encryptedPassword = hashPasswordSHA256(_registerData.value.password)
            )

            val result = registerUseCase(request)

            if(result) _registerState.value = _registerState.value.copy(isSuccess = true)
            else _registerState.value = _registerState.value.copy(isError = true)

            _registerState.value = _registerState.value.copy(isLoading = false)
        }
    }
}