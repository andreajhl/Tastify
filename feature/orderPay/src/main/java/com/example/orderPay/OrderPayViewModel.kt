package com.example.orderPay

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.library.utils.isValidShortDate
import com.example.library.utils.isValidText
import com.example.useCase.orders.OrderPaymentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface OrderPayContract {
    val payState: StateFlow<RequestState>
    val payData: StateFlow<OrderPayData>
    val errorMsg: StateFlow<OrderPayErrorState>

    fun updatePayDataField(key: String, value: String)
    fun validateOwnerName(): Boolean
    fun validateCardNumber(): Boolean
    fun validateExpiryDate(): Boolean
    fun validateSecurityCode(): Boolean
    fun isValidateData(): Boolean
    fun paymentExecute()
}

data class RequestState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: Boolean? = null
)

data class OrderPayData(
    val ownerName: String = "",
    val cardNumber: String = "",
    val expiryDate: String = "",
    val securityCode: String = "",
)

data class OrderPayErrorState(
    val ownerName: Boolean? = false,
    val cardNumber: Boolean? = false,
    val expiryDate: Boolean? = false,
    val securityCode: Boolean? = false,
)

@HiltViewModel
class OrderPayViewModel @Inject constructor(
    private val orderPaymentUseCase: OrderPaymentUseCase
): ViewModel(), OrderPayContract {
    private val _payData = MutableStateFlow(OrderPayData())
    private val _payState = MutableStateFlow(RequestState())
    private val _errorMsg = MutableStateFlow(OrderPayErrorState())

    override val payData: StateFlow<OrderPayData> get() = _payData
    override val payState: StateFlow<RequestState> get() = _payState
    override val errorMsg: StateFlow<OrderPayErrorState> get() = _errorMsg

    override fun updatePayDataField(key: String, value: String) {
        val current = _payData.value
        val updated = when (key) {
            "ownerName" -> current.copy(ownerName = value)
            "cardNumber" -> current.copy(cardNumber = value)
            "expiryDate" -> current.copy(expiryDate = value)
            "securityCode" -> current.copy(securityCode = value)
            else -> current
        }
        _payData.value = updated
    }

    override fun validateOwnerName(): Boolean {
        val isValid = isValidText(_payData.value.ownerName)

        _errorMsg.value = _errorMsg.value.copy(ownerName = !isValid)

        return isValid
    }

    override fun validateCardNumber(): Boolean {
        val cardNumber = _payData.value.cardNumber.trim()
        val isValid = cardNumber.length >= 13 && cardNumber.length < 15

        _errorMsg.value = _errorMsg.value.copy(cardNumber = !isValid)

        return isValid
    }

    override fun validateExpiryDate(): Boolean {
        var isValid = isValidShortDate(_payData.value.expiryDate)

        _errorMsg.value = _errorMsg.value.copy(expiryDate = !isValid)

        return isValid
    }

    override fun validateSecurityCode(): Boolean {
        val securityCode = _payData.value.securityCode.trim()
        val isValid = securityCode.length == 3

        _errorMsg.value = _errorMsg.value.copy(securityCode = !isValid)

        return isValid
    }

    override fun isValidateData(): Boolean {
        return validateOwnerName() &&
                validateCardNumber() &&
                validateExpiryDate() &&
                validateSecurityCode()
    }

    override fun paymentExecute() {
        viewModelScope.launch {
            _payState.value = _payState.value.copy(isLoading = true)

            val result = orderPaymentUseCase()

            if (result.isSuccess) _payState.value = _payState.value.copy(isSuccess = true)
            else _payState.value = _payState.value.copy(isError = true)

            _payState.value = _payState.value.copy(isLoading = false)
        }
    }
}