package com.example.orderDetail

import com.example.db.entities.OrderEntity
import com.example.db.entities.OrderItemEntity
import com.example.db.entities.OrderItemProduct
import com.example.useCase.orders.GetOrderDetailUseCase
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class OrderDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getOrderDetailUseCase: GetOrderDetailUseCase = mock()
    private lateinit var viewModel: OrderDetailViewModel

    @Before
    fun setup() {
        viewModel = OrderDetailViewModel(getOrderDetailUseCase)
    }

    @Test
    fun `getOrderDetail sets data and success state when useCase returns success`() = runTest {
        val orderItem = OrderItemProduct(
            order = OrderEntity(
                id = "123456",
                total = 99.99,
                timestamp = System.currentTimeMillis()
            ),
            items = listOf(
                OrderItemEntity(
                    id = 1,
                    orderId = "123456",
                    productName = "Pizza",
                    price = 50.0,
                    quantity = 1
                ),
                OrderItemEntity(
                    id = 2,
                    orderId = "123456",
                    productName = "Soda",
                    price = 2.5,
                    quantity = 2
                )
            )
        )
        whenever(getOrderDetailUseCase("123")).thenReturn(Result.success(orderItem))

        viewModel.getOrderDetail("123")

        assertEquals(orderItem, viewModel.orderDetailData.value)
        assertTrue(viewModel.orderDetailState.value.isSuccess)
        assertFalse(viewModel.orderDetailState.value.isLoading)
    }

    @Test
    fun `getOrderDetail sets error state when useCase returns failure`() = runTest {
        whenever(getOrderDetailUseCase("123")).thenReturn(Result.failure(Throwable()))

        viewModel.getOrderDetail("123")

        assertTrue(viewModel.orderDetailState.value.isError)
        assertFalse(viewModel.orderDetailState.value.isLoading)
    }
}