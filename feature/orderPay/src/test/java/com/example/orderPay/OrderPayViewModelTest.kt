package com.example.orderPay

import com.example.useCase.orders.OrderPaymentUseCase
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class OrderPayViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val orderPaymentUseCase: OrderPaymentUseCase = mock()
    private lateinit var viewModel: OrderPayViewModel

    @Before
    fun setup() {
        viewModel = OrderPayViewModel(orderPaymentUseCase)
    }

    @Test
    fun `paymentExecute sets success state when useCase returns success`() = runTest {
        whenever(orderPaymentUseCase()).thenReturn(Result.success(Unit))

        viewModel.paymentExecute()

        assertTrue(viewModel.payState.value.isSuccess)
        assertFalse(viewModel.payState.value.isLoading)
    }

    @Test
    fun `paymentExecute sets error state when useCase returns failure`() = runTest {
        whenever(orderPaymentUseCase()).thenReturn(Result.failure(Throwable("Error")))

        viewModel.paymentExecute()

        assertTrue(viewModel.payState.value.isError == true)
        assertFalse(viewModel.payState.value.isLoading)
    }
}