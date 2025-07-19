package com.example.orderPay

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.data.remote.dtos.order.OrderCreateDto
import com.example.data.remote.dtos.order.OrderItemDto
import com.example.data.remote.dtos.product.ProductUpdateDto
import com.example.data.remote.repository.cart.CartRepository
import com.example.data.remote.repository.order.OrderRepository
import com.example.data.remote.repository.product.ProductRepository
import com.example.data.worker.OrderSyncWorker
import com.example.data.worker.ProductSyncWorker
import com.example.session.SessionManager
import dagger.hilt.android.internal.Contexts.getApplication
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
    private val productService: ProductRepository,
    private val orderService: OrderRepository,
    private val cartRepository: CartRepository,
    private val sessionManager: SessionManager,
    private val app: Application
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
        val ownerName = _payData.value.ownerName.trim()
        val isValid = ownerName.length >= 2

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
        val expiryDate = _payData.value.expiryDate.trim()
        val regex = Regex("^\\d{4}$")

        var isValid = false

        if (expiryDate.matches(regex)) {
            val month = expiryDate.substring(0, 2).toIntOrNull()
            isValid = month != null && month in 1..12
        }

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
        val cartItems = cartRepository.cartState.value.items

        viewModelScope.launch {
            cartRepository.toggleShowCart()

            _payState.value = _payState.value.copy(isLoading = true)

            try {
                val orderItems = cartItems.values.map { product ->
                    OrderItemDto(
                        id = product.id,
                        productName = product.name,
                        quantity = product.quantity,
                        price = product.price
                    )
                }

                val order = orderService.createOrderLocal(
                    OrderCreateDto(
                        userId = sessionManager.getUserId()!!,
                        items = orderItems,
                        total = cartRepository.cartState.value.totalPrice,
                        timestamp = System.currentTimeMillis()
                    )
                )

                val constraints = Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()

                if (order != null) {
                    val workRequest = OneTimeWorkRequestBuilder<OrderSyncWorker>()
                        .setInputData(workDataOf("ORDER_ID" to order.id))
                        .setConstraints(constraints)
                        .build()

                    WorkManager.getInstance(getApplication(app).applicationContext)
                        .enqueueUniqueWork(
                            "OrderSync_${order.id}",
                            ExistingWorkPolicy.REPLACE,
                            workRequest
                        )
                }

                cartItems.values.forEach { product ->
                    val quantityToSubtract = product.quantity

                    productService.updateProductLocal(product.id, quantityToSubtract)

                    val workRequest = OneTimeWorkRequestBuilder<ProductSyncWorker>()
                        .setInputData(
                            workDataOf(
                                "PRODUCT_ID" to product.id,
                                "QUANTITY" to quantityToSubtract,
                                "OPERATION" to "subtract"
                            )
                        )
                        .setConstraints(constraints)
                        .build()

                    WorkManager.getInstance(getApplication(app).applicationContext)
                        .enqueueUniqueWork(
                            "ProductSync_${product.id}",
                            ExistingWorkPolicy.REPLACE,
                            workRequest
                        )
                }

                cartRepository.clearCart()
                _payState.value = _payState.value.copy(isSuccess = true)
            } catch (e: Exception) {
                Log.e("OrderPay", "Exception: ${e.message}")
                _payState.value = _payState.value.copy(isError = true)
            } finally {
                _payState.value = _payState.value.copy(isLoading = false)
            }
        }
    }
}