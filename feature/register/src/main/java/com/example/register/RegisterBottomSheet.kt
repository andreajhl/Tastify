package com.example.register

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.common.InputField
import com.example.session.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterBottomSheet(
    registerViewModel: RegisterContract,
    onDismiss: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    val state by registerViewModel.register.collectAsState()
    val errors by registerViewModel.errorMsg.collectAsState()
    val context = LocalContext.current
    val session = remember { SessionManager(context) }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showSheet by remember { mutableStateOf(true) }

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showSheet = false
                onDismiss()
            },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Text("Crear cuenta", style = MaterialTheme.typography.headlineSmall)

                Spacer(modifier = Modifier.height(16.dp))

                InputField(
                    label = "Nombre completo",
                    value = state.fullName,
                    onValueChange = { registerViewModel.updateRegisterField("fullName", it) },
                    onBlur = { registerViewModel.validateFullName() },
                    error = errors.fullName
                )

                Spacer(modifier = Modifier.height(8.dp))

                InputField(
                    label = "Email",
                    value = state.email,
                    onValueChange = { registerViewModel.updateRegisterField("email", it) },
                    onBlur = { registerViewModel.validateEmail() },
                    error = errors.email
                )

                Spacer(modifier = Modifier.height(8.dp))

                InputField(
                    label = "Contraseña",
                    value = state.password,
                    onValueChange = { registerViewModel.updateRegisterField("password", it) },
                    onBlur = { registerViewModel.validatePassword() },
                    error = errors.password,
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(8.dp))

                InputField(
                    label = "Repetir contraseña",
                    value = state.repeatPassword,
                    onValueChange = { registerViewModel.updateRegisterField("repeatPassword", it) },
                    onBlur = { registerViewModel.validateRepeatPassword() },
                    error = errors.repeatPassword,
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(24.dp))

                val isValid = registerViewModel.isValidateData()

                Button(
                    onClick = {
                        session.setLogged(true)
                        onRegisterSuccess()
                    },
                    enabled = isValid,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Registrarse")
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterBottomSheetPreview() {
    class FakeRegisterViewModel : RegisterContract {
        private val _register = MutableStateFlow(
            RegisterState(
                fullName = "Andrea Hernández",
                email = "andrea@example.com",
                password = "123456",
                repeatPassword = "123456"
            )
        )
        private val _errorMsg = MutableStateFlow(RegisterErrorState())

        override val register: StateFlow<RegisterState> get() = _register
        override val errorMsg: StateFlow<RegisterErrorState> get() = _errorMsg

        override fun updateRegisterField(key: String, value: String) {
            _register.value = when (key) {
                "fullName" -> _register.value.copy(fullName = value)
                "email" -> _register.value.copy(email = value)
                "password" -> _register.value.copy(password = value)
                "repeatPassword" -> _register.value.copy(repeatPassword = value)
                else -> _register.value
            }
        }

        override fun validateFullName(): Boolean = true
        override fun validateEmail(): Boolean = true
        override fun validatePassword(): Boolean = true
        override fun validateRepeatPassword(): Boolean = true
        override fun isValidateData(): Boolean = true
    }

    RegisterBottomSheet(
        registerViewModel = FakeRegisterViewModel(),
        onDismiss = {},
        onRegisterSuccess = {}
    )
}