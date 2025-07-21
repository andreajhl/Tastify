package com.example.remotes.cart

import com.example.db.daos.CartDao
import com.example.db.entities.CartItemEntity
import com.example.db.entities.CartItemProduct
import com.example.db.entities.ProductEntity
import com.example.remotes.repository.cart.CartRepositoryImpl
import com.example.session.SessionManager
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class CartRepositoryImplTest {

    private val cartDao: CartDao = mock()
    private val sessionManager: SessionManager = mock()
    private lateinit var repository: CartRepositoryImpl

    private val userId = "user1"

    val product = ProductEntity("1", "Pizza", "", 1000.0, "fast_food", "", 2, true, false, false)
    val cartItem = CartItemEntity("1", userId, 1, 1000.0)

    @Before
    fun setup() {
        whenever(sessionManager.getUserId()).thenReturn(userId)
        repository = CartRepositoryImpl(cartDao, sessionManager)
    }

    @Test
    fun `loadCartFromDb updates cartState with items from dao`() = runTest {
        val cartItemWithProduct = CartItemProduct(cartItem, product)

        whenever(cartDao.getItemByUserId(userId)).thenReturn(listOf(cartItemWithProduct))

        repository.loadCartFromDb()

        val state = repository.cartState.value
        assertEquals(1, state.items.size)
        assertEquals(1000.0, state.totalPrice, 0.01)
    }

    @Test
    fun `addToCart inserts item when not exists`() = runTest {
        whenever(cartDao.getItemById("1", userId)).thenReturn(null)
        whenever(cartDao.getItemByUserId(userId)).thenReturn(emptyList())

        repository.addToCart(product, 1)

        verify(cartDao).insertItem(
            CartItemEntity(
                productId = "1",
                userId = userId,
                quantity = 1,
                price = 1000.0
            )
        )
        verify(cartDao).getItemByUserId(userId)
    }

    @Test
    fun `subtractFromCart deletes item if quantity reaches 0`() = runTest {
        val cartItemWithProduct = CartItemProduct(cartItem, product)

        whenever(cartDao.getItemById("1", userId)).thenReturn(cartItemWithProduct)
        whenever(cartDao.getItemByUserId(userId)).thenReturn(emptyList())

        repository.subtractFromCart("1", 1)

        verify(cartDao).deleteItem(
            CartItemEntity(
                productId = "1",
                userId = userId,
                quantity = 1,
                price = 1000.0
            )
        )
    }

    @Test
    fun `clearCart clears dao and resets cartState`() = runTest {
        repository.clearCart()

        verify(cartDao).clear(userId)
        val state = repository.cartState.value
        assertTrue(state.items.isEmpty())
        assertFalse(state.showCart)
        assertEquals(0.0, state.totalPrice, 0.01)
    }

    @Test
    fun `toggleShowCart toggles showCart field`() {
        assertFalse(repository.cartState.value.showCart)
        repository.toggleShowCart()
        assertTrue(repository.cartState.value.showCart)
    }
}