package com.example.remotes.order

import com.example.db.daos.OrderDao
import com.example.db.entities.OrderEntity
import com.example.db.entities.OrderItemEntity
import com.example.db.entities.OrderItemProduct
import com.example.remotes.api.OrderApi
import com.example.remotes.dtos.order.OrderCreateDto
import com.example.remotes.dtos.order.OrderDto
import com.example.remotes.dtos.order.OrderItemDto
import com.example.remotes.repository.order.OrderRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.Response
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class OrderRepositoryImplTest {

    private val orderApi: OrderApi = mock()
    private val orderDao: OrderDao = mock()
    private lateinit var repository: OrderRepositoryImpl

    val orderItemDto = OrderItemDto("1", "Burger", 50.0, 1)
    val orderDto = OrderDto(
        id = "order1",
        total = 50.0,
        timestamp = 123L,
        items = listOf(orderItemDto),
        userId = "123"
    )

    @Before
    fun setup() {
        repository = OrderRepositoryImpl(orderApi, orderDao)
    }

    @Test
    fun `getOrdersRemote should store orders in DB and return them`() = runTest {
        val expectedResponse = Response.success(listOf(orderDto))
        val expectedOrders = listOf(mock<OrderItemProduct>())

        whenever(orderApi.getOrdersByUserId("user1")).thenReturn(expectedResponse)
        whenever(orderDao.getAllOrdersWithItems()).thenReturn(expectedOrders)

        val result = repository.getOrdersRemote("user1")

        verify(orderDao).createOrder(any())
        verify(orderDao).createOrderItems(any())
        assertEquals(expectedOrders, result)
    }

    @Test
    fun `getOrder should store order in DB and return it`() = runTest {
        val expectedResponse = Response.success(orderDto)
        val expectedOrder = mock<OrderItemProduct>()

        whenever(orderApi.getOrderById("order1")).thenReturn(expectedResponse)
        whenever(orderDao.getOrderWithItems("order1")).thenReturn(expectedOrder)

        val result = repository.getOrder("order1")

        verify(orderDao).createOrder(any())
        verify(orderDao).createOrderItems(any())
        assertEquals(expectedOrder, result)
    }

    @Test
    fun `createOrderLocal should persist order and items and return entity`() = runTest {
        val dto = OrderCreateDto(
            userId = "user1",
            total = 200.0,
            items = listOf(orderItemDto),
            timestamp = System.currentTimeMillis()
        )

        val result = repository.createOrderLocal(dto)

        verify(orderDao).createOrder(any())
        verify(orderDao).createOrderItems(any())
        assertNotNull(result)
        assertEquals(200.0, result.total)
    }

    @Test
    fun `getOrdersLocal should return local orders`() = runTest {
        val expectedOrders = listOf(mock<OrderItemProduct>())
        whenever(orderDao.getAllOrdersWithItems()).thenReturn(expectedOrders)

        val result = repository.getOrdersLocal()

        assertEquals(expectedOrders, result)
    }

    @Test
    fun `createOrderRemote should call API and return true if successful`() = runTest {
        val orderItemProduct = OrderItemProduct(
            order = OrderEntity("order1", 150.0, 12345L),
            items = listOf(
                OrderItemEntity(0, "order1", "Burger", 50.0, 3)
            )
        )

        whenever(orderDao.getOrderWithItems("order1")).thenReturn(orderItemProduct)
        whenever(orderApi.createOrder(any())).thenReturn(Response.success(orderDto))

        val result = repository.createOrderRemote("order1", "user1")

        verify(orderApi).createOrder(any())
        assertTrue(result)
    }
}