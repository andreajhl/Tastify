package com.example.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.common.InputField
import com.example.session.SessionManager
import com.example.theme.ui.theme.MainColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterBottomSheet(
    onDismiss: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    val registerViewModel: RegisterViewModel = hiltViewModel()

    val state by registerViewModel.register.collectAsState()
    val errors by registerViewModel.errorMsg.collectAsState()

    val context = LocalContext.current

    val session = remember { SessionManager(context) }
    var showSheet by remember { mutableStateOf(true) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val isValid = registerViewModel.isValidateData()

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
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(stringResource(R.string.register_title), style = MaterialTheme.typography.headlineSmall)

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    InputField(
                        label = stringResource(R.string.input_name),
                        value = state.name,
                        onValueChange = { registerViewModel.updateRegisterField("name", it) },
                        onBlur = { registerViewModel.validateName() },
                        error = if(errors.name == true) stringResource(R.string.input_name_error) else ""
                    )

                    InputField(
                        label = stringResource(R.string.input_last_name),
                        value = state.lastName,
                        onValueChange = { registerViewModel.updateRegisterField("lastName", it) },
                        onBlur = { registerViewModel.validateLastName() },
                        error = if(errors.lastName == true) stringResource(R.string.input_last_name_error) else ""
                    )

                    InputField(
                        label = stringResource(R.string.input_email),
                        value = state.email,
                        onValueChange = { registerViewModel.updateRegisterField("email", it) },
                        onBlur = { registerViewModel.validateEmail() },
                        error = if(errors.email == true) stringResource(R.string.input_email_error) else ""
                    )

                    InputField(
                        label = stringResource(R.string.input_password),
                        value = state.password,
                        onValueChange = { registerViewModel.updateRegisterField("password", it) },
                        onBlur = { registerViewModel.validatePassword() },
                        visualTransformation = PasswordVisualTransformation(),
                        error = if(errors.password == true) stringResource(R.string.input_password_error) else ""
                    )

                    InputField(
                        label = stringResource(R.string.input_repeat_password),
                        value = state.repeatPassword,
                        onValueChange = { registerViewModel.updateRegisterField("repeatPassword", it) },
                        onBlur = { registerViewModel.validateRepeatPassword() },
                        visualTransformation = PasswordVisualTransformation(),
                        error = if(errors.repeatPassword == true) stringResource(R.string.input_repeat_password_error) else ""
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        session.setLogged(true)
                        onRegisterSuccess()
                    },
                    enabled = isValid,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MainColor)
                ) {
                    Text(stringResource(R.string.sign_up))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterBottomSheetPreview() {
    RegisterBottomSheet(
        onDismiss = {},
        onRegisterSuccess = {}
    )
}