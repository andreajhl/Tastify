package com.example.orderDetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.remote.repository.order.OrderRepository
import com.example.db.entities.OrderItemProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface OrderDetailContract {
    val orderDetailData: StateFlow<OrderItemProduct?>
    val orderDetailState: MutableStateFlow<OrderDetailState>
    fun getOrderDetail(id: String)
}

data class OrderDetailState(
    val isLoading: Boolean = true,
    val isSuccess: Boolean = false,
    val isError: Boolean = false
)
@HiltViewModel
class OrderDetailViewModel @Inject constructor(
    private val orderService: OrderRepository
): ViewModel(), OrderDetailContract {
    private val _orderDetailData = MutableStateFlow<OrderItemProduct?>(null)
    override val orderDetailData: StateFlow<OrderItemProduct?> = _orderDetailData

    private val _orderDetailState = MutableStateFlow(OrderDetailState())
    override val orderDetailState: MutableStateFlow<OrderDetailState> get() = _orderDetailState

    override fun getOrderDetail(id: String) {
        viewModelScope.launch {
            _orderDetailState.value = OrderDetailState(isLoading = true)

            try {
                val remote = orderService.getOrder(id)

                if (remote != null) {
                    _orderDetailData.value = remote
                }

                _orderDetailState.value = _orderDetailState.value.copy(isSuccess = true)
            } catch (e: Exception) {
                Log.e("OrderDetail", "Exception: ${e.message}")
                _orderDetailState.value = _orderDetailState.value.copy(isError = true)
            } finally {
                _orderDetailState.value = _orderDetailState.value.copy(isLoading = false)
            }
        }
    }
}