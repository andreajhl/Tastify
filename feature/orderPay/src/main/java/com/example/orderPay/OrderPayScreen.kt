package com.example.orderPay

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.navigation.Screen
import com.example.theme.ui.theme.DefaultScreenPadding
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderPayScreen(navController: NavHostController) {
    val coroutineScope = rememberCoroutineScope()

    val orderPayViewModel: OrderPayViewModel = hiltViewModel()

    val errorState by orderPayViewModel.errorMsg.collectAsState()
    val payData by orderPayViewModel.payData.collectAsState()
    val payState by orderPayViewModel.payState.collectAsState()

    var isBackVisible by remember { mutableStateOf(false) }

    fun handleBlur(field: String) {
        return when (field) {
            "cardNumber" -> orderPayViewModel.validateCardNumber()
            "ownerName" -> orderPayViewModel.validateOwnerName()
            "expiryDate" -> orderPayViewModel.validateExpiryDate()
            "securityCode" -> orderPayViewModel.validateSecurityCode()
            else -> {}
        } as Unit
    }

    fun handlePayClick() {
        coroutineScope.launch {
            orderPayViewModel.paymentExecute()
            navController.navigate(Screen.Home.route)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.order_pay_title),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(Screen.Home.route)
                    }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.order_pay_comeback)
                        )
                    }
                },
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(DefaultScreenPadding),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            OrderPayContent(
                state = payData,
                errors = errorState,
                loading = payState.isLoading,
                isBackVisible = isBackVisible,
                onBackFocusChanged = { isBackVisible = it },
                isValidateData = { orderPayViewModel.isValidateData() },
                onValueChange = { field, value -> orderPayViewModel.updatePayDataField(field, value) },
                onBlur = { field -> handleBlur(field) },
                onPayClick = { handlePayClick() }
            )
        }
    }
}