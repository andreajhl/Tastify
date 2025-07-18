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

    LaunchedEffect(registerState.isSuccess) {
        if (registerState.isSuccess == true) {
            showSheet = false
            onRegisterSuccess()
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
