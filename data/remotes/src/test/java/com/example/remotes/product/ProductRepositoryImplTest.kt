package com.example.remotes.product

import com.example.db.daos.ProductDao
import com.example.db.entities.ProductEntity
import com.example.remotes.api.ProductsApi
import com.example.remotes.dtos.product.ProductDto
import com.example.remotes.dtos.product.ProductUpdateDto
import com.example.remotes.repository.product.ProductRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.Response
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@OptIn(ExperimentalCoroutinesApi::class)
class ProductRepositoryImplTest {

    private val dao: ProductDao = mock()
    private val api: ProductsApi = mock()
    private lateinit var repository: ProductRepositoryImpl

    val productDto = ProductDto("1", "Pizza", "", 1000.0, 10, "fast_food", "", true, false, false)

    @Before
    fun setup() {
        repository = ProductRepositoryImpl(dao, api)
    }

    @Test
    fun `getProductListLocal should call dao and return products`() = runTest {
        val products = listOf(
            ProductEntity("1", "Pizza", "", 1000.0, "fast_food", "", 5, true, false, false)
        )

        whenever(dao.getAll()).thenReturn(products)

        val result = repository.getProductListLocal()

        verify(dao).getAll()
        assertEquals(products, result)
    }

    @Test
    fun `getProductListRemote should fetch from API and update dao`() = runTest {
        val apiProducts = listOf(productDto)

        whenever(api.getProducts()).thenReturn(Response.success(apiProducts))
        whenever(dao.getAll()).thenReturn(
            apiProducts.map {
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
        )

        val result = repository.getProductListRemote()

        verify(api).getProducts()
        verify(dao).clearProducts()
        verify(dao).insertAll(any())
        verify(dao).getAll()
        assertEquals(1, result.size)
    }

    @Test
    fun `updateProductLocal should update dao when product exists`() = runTest {
        val product = ProductEntity("1", "Pizza", "", 10.0, "fast_food", "", 5, true, false, false)
        whenever(dao.getProductById("1")).thenReturn(product)

        repository.updateProductLocal("1", 2)

        argumentCaptor<ProductEntity>().apply {
            verify(dao).updateProduct(capture())
            assertEquals(3, firstValue.quantity)
        }
    }

    @Test
    fun `updateProductRemote should update dao and return updated product`() = runTest {
        whenever(api.updateProduct(eq("1"), any())).thenReturn(Response.success(productDto))

        val result = repository.updateProductRemote(
            "1", ProductUpdateDto(
                quantity = 3,
                method = "subtract"
            )
        )

        verify(api).updateProduct(eq("1"), any())

        argumentCaptor<ProductEntity>().apply {
            verify(dao).updateProduct(capture())
            assertEquals(productDto.id, firstValue.id)
            assertEquals(productDto.name, firstValue.name)
            assertEquals(10, firstValue.quantity)
        }

        assertNotNull(result)
        assertEquals(productDto.id, result.id)
        assertEquals(10, result.quantity)
    }
}