package com.example.orderHistory

import com.example.db.entities.OrderEntity
import com.example.db.entities.OrderItemEntity
import com.example.db.entities.OrderItemProduct
import com.example.useCase.orders.GetOrderHistoryUseCase
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
class OrderHistoryViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getOrderHistoryUseCase: GetOrderHistoryUseCase = mock()
    private lateinit var viewModel: OrderHistoryViewModel

    @Before
    fun setup() {
        viewModel = OrderHistoryViewModel(getOrderHistoryUseCase)
    }

    @Test
    fun `getOrderHistory sets data and success when useCase returns success`() = runTest {
        val orderHistory = listOf(
            OrderItemProduct(
                order = OrderEntity(
                    id = "order_1",
                    total = 150.0,
                    timestamp = System.currentTimeMillis()
                ),
                items = listOf(
                    OrderItemEntity(
                        id = 1,
                        orderId = "order_1",
                        productName = "Product 1",
                        price = 50.0,
                        quantity = 1
                    ),
                    OrderItemEntity(
                        id = 2,
                        orderId = "order_1",
                        productName = "Product 2",
                        price = 100.0,
                        quantity = 1
                    )
                )
            )
        )

        whenever(getOrderHistoryUseCase()).thenReturn(Result.success(orderHistory))

        viewModel.getOrderHistory()

        assertEquals(orderHistory, viewModel.orderHistoryData.value)
        assertTrue(viewModel.orderHistoryState.value.isSuccess)
        assertFalse(viewModel.orderHistoryState.value.isLoading)
    }

    @Test
    fun `getOrderHistory sets error when useCase returns failure`() = runTest {
        whenever(getOrderHistoryUseCase()).thenReturn(Result.failure(Throwable()))

        viewModel.getOrderHistory()

        assertTrue(viewModel.orderHistoryState.value.isError)
        assertFalse(viewModel.orderHistoryState.value.isLoading)
    }
}