package com.example.data.remote.repository.product

import com.example.data.remote.api.ProductsApi
import com.example.db.daos.ProductDao
import com.example.db.entities.ProductEntity
import jakarta.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val dao: ProductDao,
    private val api: ProductsApi
) : ProductRepository {
    override suspend fun getAll(): List<ProductEntity> = dao.getAll()

    override suspend fun getAllRemote(): List<ProductEntity> {
        val res = api.getProducts()

        if (res.body() != null && res.body()!!.isNotEmpty()) {
            val products = res.body()!!

            val entities = products.map { it ->
                ProductEntity(
                    name = it.name,
                    description = it.description,
                    price = it.price,
                    category = it.category,
                    imageUrl = it.imageUrl,
                    quantity = it.quantity,
                    hasDrink = it.hasDrink,
                    glutenFree = it.glutenFree,
                    vegetarian = it.vegetarian
                )
            }

            dao.insertAll(entities)
        }
        return dao.getAll()
    }
}