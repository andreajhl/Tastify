package com.example.data.remote.repository.product

import com.example.data.remote.api.ProductsApi
import com.example.data.remote.dtos.product.ProductUpdateDto
import com.example.db.daos.ProductDao
import com.example.db.entities.ProductEntity
import javax.inject.Inject

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
                    id = it.id,
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

    override suspend fun updateProduct(id: String, body: ProductUpdateDto): ProductEntity? {
        val res = api.updateProduct(id, body);

        if (res.body() != null) {
            val product = res.body()!!

            val productUpdate = ProductEntity(
                id = product.id,
                name = product.name,
                description = product.description,
                price = product.price,
                category = product.category,
                imageUrl = product.imageUrl,
                quantity = product.quantity,
                hasDrink = product.hasDrink,
                glutenFree = product.glutenFree,
                vegetarian = product.vegetarian
            )

            dao.updateProduct(productUpdate)

            return productUpdate
        }

        return null
    }

}