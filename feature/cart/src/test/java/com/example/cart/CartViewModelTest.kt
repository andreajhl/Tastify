package com.example.cart

import com.example.db.entities.ProductEntity
import com.example.remotes.repository.cart.CartRepository
import com.example.remotes.repository.cart.CartState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class CartViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val cartRepository: CartRepository = mock()
    private lateinit var viewModel: CartViewModel

    @Before
    fun setup() {
        viewModel = CartViewModel(cartRepository)
    }

    @Test
    fun `getCart should call loadCartFromDb on repository`() = runTest {
        viewModel.getCart()
        verify(cartRepository).loadCartFromDb()
    }

    @Test
    fun `addToCart should call addToCart on repository`() = runTest {
        val product =
            ProductEntity("1", "Hamburger", "", 1500.0, "fast_food", "", 2, true, false, false)
        viewModel.addToCart(product, 2)
        verify(cartRepository).addToCart(product, 2)
    }

    @Test
    fun `subtractToCart should call subtractFromCart on repository`() = runTest {
        viewModel.subtractToCart("1", 1)
        verify(cartRepository).subtractFromCart("1", 1)
    }

    @Test
    fun `clearCart should call clearCart on repository`() = runTest {
        viewModel.clearCart()
        verify(cartRepository).clearCart()
    }

    @Test
    fun `removeItemCart should call removeItem on repository`() = runTest {
        val callback: (Int) -> Unit = {}
        viewModel.removeItemCart("1", callback)
        verify(cartRepository).removeItem(eq("1"), any())
    }

    @Test
    fun `getTotalItems should return correct sum from cart state`() = runTest {
        val product1 = ProductEntity(
            "1",
            "Falafel",
            "desc",
            1100.0,
            "fast_food",
            "url",
            10,
            true,
            false,
            false
        )
        val product2 = ProductEntity(
            "2",
            "Falafel",
            "desc",
            1100.0,
            "fast_food",
            "url",
            10,
            true,
            false,
            false
        )

        val cartState = CartState(
            items = mapOf(
                "1" to product1,
                "2" to product2
            )
        )
        whenever(cartRepository.cartState).thenReturn(MutableStateFlow(cartState))

        val viewModel = CartViewModel(cartRepository)
        assertEquals(20, viewModel.getTotalItems())
    }

    @Test
    fun `toggleShowCart should call toggleShowCart on repository`() = runTest {
        viewModel.toggleShowCart()
        verify(cartRepository).toggleShowCart()
    }
}