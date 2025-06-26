package com.example.register

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.common.InputField
import com.example.session.SessionManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterBottomSheet(
    viewModel: RegisterViewModel,
    onDismiss: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    val state by viewModel.register.collectAsState()
    val errors by viewModel.errorMsg.collectAsState()
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
                    onValueChange = { viewModel.updateRegisterField("fullName", it) },
                    onBlur = { viewModel.validateFullName() },
                    error = errors.fullName
                )

                Spacer(modifier = Modifier.height(8.dp))

                InputField(
                    label = "Email",
                    value = state.email,
                    onValueChange = { viewModel.updateRegisterField("email", it) },
                    onBlur = { viewModel.validateEmail() },
                    error = errors.email
                )

                Spacer(modifier = Modifier.height(8.dp))

                InputField(
                    label = "Contraseña",
                    value = state.password,
                    onValueChange = { viewModel.updateRegisterField("password", it) },
                    onBlur = { viewModel.validatePassword() },
                    error = errors.password,
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(8.dp))

                InputField(
                    label = "Repetir contraseña",
                    value = state.repeatPassword,
                    onValueChange = { viewModel.updateRegisterField("repeatPassword", it) },
                    onBlur = { viewModel.validateRepeatPassword() },
                    error = errors.repeatPassword,
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(24.dp))

                val isValid = viewModel.isValidateData()

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