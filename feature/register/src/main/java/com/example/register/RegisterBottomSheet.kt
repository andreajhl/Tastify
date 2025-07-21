package com.example.register

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.library.utils.SnackbarManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterBottomSheet(
    onDismiss: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    val context = LocalContext.current

    val registerViewModel: RegisterViewModel = hiltViewModel()

    val registerError by registerViewModel.errorMsg.collectAsState()
    val registerState by registerViewModel.registerState.collectAsState()
    val registerData by registerViewModel.registerData.collectAsState()

    var showSheet by remember { mutableStateOf(true) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val isFormValid by remember(registerData, registerError) {
        derivedStateOf { registerViewModel.isValidateData() }
    }

    fun validateFieldByKey(key: String): Boolean {
        return when (key) {
            "name" -> registerViewModel.validateName()
            "lastName" -> registerViewModel.validateLastName()
            "email" -> registerViewModel.validateEmail()
            "password" -> registerViewModel.validatePassword()
            "repeatPassword" -> registerViewModel.validateRepeatPassword()
            else -> false
        }
    }

    LaunchedEffect(registerState) {
        if (registerState.isSuccess == true) {
            showSheet = false
            onRegisterSuccess()
        }

        if (registerState.isError == true) {
            SnackbarManager.showMessage(
                actionLabel = context.getString(R.string.register_failed_action),
                message = context.getString(R.string.register_failed),
                onAction = { registerViewModel.executeRegister() }
            )
        }
    }

    if (showSheet) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                showSheet = false
                onDismiss()
            }
        ) {
            RegisterBottomContent(
                registerData = registerData,
                registerError = registerError,
                isFormValid = isFormValid,
                isLoading = registerState.isLoading,
                onRegister = { registerViewModel.executeRegister() },
                updateField = { key, value -> registerViewModel.updateRegisterField(key, value) },
                validateField = { key -> validateFieldByKey(key) }
            )
        }
    }
}
