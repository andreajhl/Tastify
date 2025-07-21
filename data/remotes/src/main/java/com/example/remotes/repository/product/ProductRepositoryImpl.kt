package com.example.remotes.repository.product

import com.example.db.daos.ProductDao
import com.example.db.entities.ProductEntity
import com.example.remotes.api.ProductsApi
import com.example.remotes.dtos.product.ProductUpdateDto
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val dao: ProductDao,
    private val api: ProductsApi
) : ProductRepository {
    override suspend fun getProductListLocal(): List<ProductEntity> = dao.getAll()

    override suspend fun getProductListLocalRemote(): List<ProductEntity> {
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

            dao.clearProducts()
            dao.insertAll(entities)
        }

        return dao.getAll()
    }

    override suspend fun updateProductLocal(id: String, quantityToSubtract: Int) {
        val product = dao.getProductById(id)

        if (product !== null) {
            val updatedQuantity = (product.quantity - quantityToSubtract).coerceAtLeast(0)
            val updatedProduct = product.copy(quantity = updatedQuantity)
            dao.updateProduct(updatedProduct)
        }
    }

    override suspend fun updateProductRemote(id: String, body: ProductUpdateDto): ProductEntity? {
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