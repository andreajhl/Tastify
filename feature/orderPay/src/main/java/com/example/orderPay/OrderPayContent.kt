package com.example.orderPay

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.common.InputField
import com.example.theme.ui.theme.MainColor

@Composable
fun OrderPayContent(
    loading: Boolean,
    state: OrderPayData,
    errors: OrderPayErrorState,
    isBackVisible: Boolean,
    onBackFocusChanged: (Boolean) -> Unit,
    onValueChange: (String, String) -> Unit,
    onBlur: (String) -> Unit,
    isValidateData: () -> Boolean,
    onPayClick: () -> Unit
) {
    fun formatExpiryDate(input: String): String {
        val digits = input.filter { it.isDigit() }.take(4)
        return when {
            digits.length >= 3 -> "${digits.take(2)}/${digits.drop(2)}"
            digits.isNotEmpty() -> digits
            else -> ""
        }
    }

    val isFormValid by remember(state, errors) {
        derivedStateOf { isValidateData() }
    }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        CreditCardScreen(
            cardNumber = state.cardNumber,
            ownerName = state.ownerName,
            expiryDate = state.expiryDate,
            cvv = state.securityCode,
            isBackVisible = isBackVisible
        )

        InputField(
            label = stringResource(R.string.card_number_input_label),
            value = state.cardNumber,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { onValueChange("cardNumber", it) },
            onBlur = { onBlur("cardNumber") },
            error = if (errors.cardNumber == true) stringResource(R.string.card_number_error_label) else ""
        )

        InputField(
            label = stringResource(R.string.owner_input_label),
            value = state.ownerName,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { onValueChange("ownerName", it) },
            onBlur = { onBlur("ownerName") },
            error = if (errors.ownerName == true) stringResource(R.string.owner_error_label) else ""
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            InputField(
                label = stringResource(R.string.expiry_input_label),
                value = state.expiryDate,
                pattern = { formatExpiryDate(it) },
                keyboardType = KeyboardType.Number,
                modifier = Modifier.weight(1f),
                onValueChange = { onValueChange("expiryDate", it) },
                onBlur = { onBlur("expiryDate") },
                error = if (errors.expiryDate == true) stringResource(R.string.expiry_error_label) else ""
            )

            InputField(
                label = stringResource(R.string.security_code_input_label),
                value = state.securityCode,
                keyboardType = KeyboardType.Number,
                modifier = Modifier
                    .weight(1f)
                    .onFocusChanged { onBackFocusChanged(it.isFocused) },
                onValueChange = { onValueChange("securityCode", it) },
                onBlur = { onBlur("securityCode") },
                error = if (errors.securityCode == true) stringResource(R.string.security_code_error_label) else ""
            )
        }
    }

    Button(
        onClick = onPayClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp),
        enabled = isFormValid && !loading,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MainColor)
    ) {
        Text(stringResource(R.string.pay_label))
    }
}

@Preview(showBackground = true)
@Composable
fun OrderPayContentPreview() {
    val fakeState = OrderPayData(
        cardNumber = "1234 5678 9012 3456",
        ownerName = "Andrea Hernández",
        expiryDate = "12/26",
        securityCode = "123"
    )

    val fakeErrors = OrderPayErrorState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        OrderPayContent(
            loading = false,
            state = fakeState,
            errors = fakeErrors,
            isBackVisible = false,
            onBackFocusChanged = {},
            onValueChange = { _, _ -> },
            onBlur = {},
            isValidateData = { true },
            onPayClick = {}
        )
    }
}