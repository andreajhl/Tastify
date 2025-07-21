package com.example.orderHistory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.db.entities.OrderItemProduct
import com.example.useCase.orders.GetOrderHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface OrderHistoryContract {
    val orderHistoryData: StateFlow<List<OrderItemProduct>>
    val orderHistoryState: MutableStateFlow<OrderHistoryState>
    fun getOrderHistory()
}

data class OrderHistoryState(
    val isLoading: Boolean = true,
    val isSuccess: Boolean = false,
    val isError: Boolean = false
)
@HiltViewModel
class OrderHistoryViewModel @Inject constructor(
    private val getOrderHistoryUseCase: GetOrderHistoryUseCase
): ViewModel(), OrderHistoryContract {
    private var _orderHistoryData = MutableStateFlow<List<OrderItemProduct>>(emptyList())
    override val orderHistoryData = _orderHistoryData

    private val _orderHistoryState = MutableStateFlow(OrderHistoryState())
    override val orderHistoryState: MutableStateFlow<OrderHistoryState> get() = _orderHistoryState

    override fun getOrderHistory() {
        viewModelScope.launch {
            _orderHistoryState.value = OrderHistoryState(isLoading = true)

            val result = getOrderHistoryUseCase()

            if (result.isSuccess) {
                _orderHistoryData.value = result.getOrNull() ?: emptyList()
                _orderHistoryState.value = _orderHistoryState.value.copy(isSuccess = true)
            } else {
                _orderHistoryState.value = _orderHistoryState.value.copy(isError = true)
            }

            _orderHistoryState.value = _orderHistoryState.value.copy(isLoading = false)
        }
    }
}