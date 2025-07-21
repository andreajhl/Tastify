package com.example.remotes.repository.order

import com.example.db.daos.OrderDao
import com.example.db.entities.OrderEntity
import com.example.db.entities.OrderItemEntity
import com.example.db.entities.OrderItemProduct
import com.example.remotes.api.OrderApi
import com.example.remotes.dtos.order.OrderCreateDto
import com.example.remotes.dtos.order.OrderItemDto
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val api: OrderApi,
    private val orderDao: OrderDao
) : OrderRepository {
    override suspend fun getOrdersRemote(id: String): List<OrderItemProduct> {
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

    override suspend fun createOrderLocal(request: OrderCreateDto): OrderEntity? {
        val localOrderId = java.util.UUID.randomUUID().toString()

        val entity = OrderEntity(
            id = localOrderId,
            total = request.total,
            timestamp = System.currentTimeMillis()
        )

        orderDao.createOrder(entity)

        val items = request.items.map { itemDto ->
            OrderItemEntity(
                orderId = localOrderId,
                productName = itemDto.productName ?: "",
                price = itemDto.price ?: 0.0,
                quantity = itemDto.quantity ?: 0
            )
        }

        orderDao.createOrderItems(items)

        return entity
    }

    override suspend fun getOrdersLocal(): List<OrderItemProduct> {
        return orderDao.getAllOrdersWithItems()
    }

    override suspend fun createOrderRemote(orderId: String, userId: String): Boolean {
        val orderWithItems = orderDao.getOrderWithItems(orderId)

        val request = OrderCreateDto(
            userId = userId,
            total = orderWithItems.order.total,
            items = orderWithItems.items.map { item ->
                OrderItemDto(
                    id = item.id.toString(),
                    productName = item.productName,
                    price = item.price,
                    quantity = item.quantity,
                )
            },
            timestamp = System.currentTimeMillis()
        )

        val response = api.createOrder(request)
        return response.isSuccessful
    }
}