package com.example.orderHistory

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.remote.repository.order.OrderRepository
import com.example.db.entities.OrderItemProduct
import com.example.session.SessionManager
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
    private val orderService: OrderRepository,
    private val sessionManager: SessionManager
): ViewModel(), OrderHistoryContract {
    private var _orderHistoryData = MutableStateFlow<List<OrderItemProduct>>(emptyList())
    override val orderHistoryData = _orderHistoryData

    private val _orderHistoryState = MutableStateFlow(OrderHistoryState())
    override val orderHistoryState: MutableStateFlow<OrderHistoryState> get() = _orderHistoryState

    override fun getOrderHistory() {
        viewModelScope.launch {
            _orderHistoryState.value = OrderHistoryState(isLoading = true)

            try {
                val local = orderService.getAll()

                if (local.isEmpty()) {
                    val userId = sessionManager.getUserId()
                    val remote = orderService.getAllRemote(userId!!)

                    _orderHistoryData.value = remote
                } else {
                    _orderHistoryData.value = local
                }

                _orderHistoryState.value = _orderHistoryState.value.copy(isSuccess = true)
            } catch (e: Exception) {
                Log.e("OrderHistory", "Exception: ${e.message}")
                _orderHistoryState.value = _orderHistoryState.value.copy(isError = true)
            } finally {
                _orderHistoryState.value = _orderHistoryState.value.copy(isLoading = false)
            }
        }
    }
}