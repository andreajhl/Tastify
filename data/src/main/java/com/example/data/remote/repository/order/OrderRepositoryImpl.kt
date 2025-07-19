package com.example.data.remote.repository.order

import com.example.data.remote.api.OrderApi
import com.example.data.remote.dtos.order.OrderCreateDto
import com.example.db.daos.OrderDao
import com.example.db.entities.OrderEntity
import com.example.db.entities.OrderItemEntity
import com.example.db.entities.OrderItemProduct
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val api: OrderApi,
    private val orderDao: OrderDao
) : OrderRepository {
    override suspend fun getAllRemote(id: String): List<OrderItemProduct> {
        val res = api.getOrdersByUserId(id)

        if (res.body() != null && res.body()!!.isNotEmpty()) {
            val ordersDto = res.body()!!

            ordersDto.forEach { dto ->
                val orderEntity = OrderEntity(
                    id = dto.id,
                    total = dto.total,
                    timestamp = dto.timestamp
                )

                orderDao.createOrder(orderEntity)

                val items = dto.items.map { itemDto ->
                    OrderItemEntity(
                        orderId = dto.id,
                        productName = itemDto.productName ?: "",
                        price = itemDto.price ?: 0.0,
                        quantity = itemDto.quantity ?: 0
                    )
                }
                orderDao.createOrderItems(items)
            }

            return orderDao.getAllOrdersWithItems()
        }

        return emptyList()
    }

    override suspend fun getOrder(id: String): OrderItemProduct? {
        val res = api.getOrderById(id)

        if (res.body() != null) {
            val orderDto = res.body()!!

            val orderEntity = OrderEntity(
                id = orderDto.id,
                total = orderDto.total,
                timestamp = orderDto.timestamp
            )
            orderDao.createOrder(orderEntity)

            val items = orderDto.items.map { itemDto ->
                OrderItemEntity(
                    orderId = orderDto.id,
                    productName = itemDto.productName.orEmpty(),
                    price = itemDto.price ?: 0.0,
                    quantity = itemDto.quantity ?: 0
                )
            }
            orderDao.createOrderItems(items)

            return orderDao.getOrderWithItems(orderDto.id)
        }

        return null
    }

    override suspend fun createOrder(request: OrderCreateDto): OrderEntity? {
        val res = api.createOrder(request)

        if (res.isSuccessful && res.body() != null) {
            val order = res.body()!!

            val entity = OrderEntity(
                id = order.id,
                total = order.total,
                timestamp = order.timestamp
            )

            orderDao.createOrder(entity)
            return entity
        }

        return null
    }

    override suspend fun getAll(): List<OrderItemProduct> {
        return orderDao.getAllOrdersWithItems()
    }
}