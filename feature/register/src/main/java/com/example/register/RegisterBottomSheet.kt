package com.example.register

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.common.InputField
import com.example.theme.ui.theme.MainColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterBottomSheet(
    onDismiss: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    val registerViewModel: RegisterViewModel = hiltViewModel()

    val errorState by registerViewModel.errorMsg.collectAsState()
    val registerState by registerViewModel.registerState.collectAsState()
    val registerData by registerViewModel.registerData.collectAsState()

    var showSheet by remember { mutableStateOf(true) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val isFormValid by remember(registerData, errorState) {
        derivedStateOf { registerViewModel.isValidateData() }
    }

    LaunchedEffect(registerState.isSuccess) {
        if (registerState.isSuccess == true) {
            showSheet = false
            onRegisterSuccess()
        }
    }

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
                        value = registerData.name,
                        onValueChange = { registerViewModel.updateRegisterField("name", it) },
                        onBlur = { registerViewModel.validateName() },
                        error = if(errorState.name == true) stringResource(R.string.input_name_error) else ""
                    )

                    InputField(
                        label = stringResource(R.string.input_last_name),
                        value = registerData.lastName,
                        onValueChange = { registerViewModel.updateRegisterField("lastName", it) },
                        onBlur = { registerViewModel.validateLastName() },
                        error = if(errorState.lastName == true) stringResource(R.string.input_last_name_error) else ""
                    )

                    InputField(
                        label = stringResource(R.string.input_email),
                        value = registerData.email,
                        onValueChange = { registerViewModel.updateRegisterField("email", it) },
                        onBlur = { registerViewModel.validateEmail() },
                        error = if(errorState.email == true) stringResource(R.string.input_email_error) else ""
                    )

                    InputField(
                        label = stringResource(R.string.input_password),
                        value = registerData.password,
                        onValueChange = { registerViewModel.updateRegisterField("password", it) },
                        onBlur = { registerViewModel.validatePassword() },
                        visualTransformation = PasswordVisualTransformation(),
                        error = if(errorState.password == true) stringResource(R.string.input_password_error) else ""
                    )

                    InputField(
                        label = stringResource(R.string.input_repeat_password),
                        value = registerData.repeatPassword,
                        onValueChange = { registerViewModel.updateRegisterField("repeatPassword", it) },
                        onBlur = { registerViewModel.validateRepeatPassword() },
                        visualTransformation = PasswordVisualTransformation(),
                        error = if(errorState.repeatPassword == true) stringResource(R.string.input_repeat_password_error) else ""
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { registerViewModel.executeRegister() },
                    enabled = isFormValid && !registerState.isLoading,
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