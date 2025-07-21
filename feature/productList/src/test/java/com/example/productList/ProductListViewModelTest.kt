package com.example.productList

import com.example.db.entities.ProductEntity
import com.example.useCase.productList.GetProductsUseCase
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class ProductListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getProductsUseCase: GetProductsUseCase = mock()
    private lateinit var viewModel: ProductListViewModel

    @Before
    fun setup() {
        viewModel = ProductListViewModel(getProductsUseCase)
    }

    @Test
    fun `getProducts success updates productList and state`() = runTest {
        val products = listOf(
            ProductEntity("1", "Pizza", "url", 1000.0, "fast_food", "", 5, true, false, false),
            ProductEntity("2", "Salad", "url", 500.0, "salad", "", 10, false, true, false)
        )
        whenever(getProductsUseCase()).thenReturn(Result.success(products))

        viewModel.getProducts()

        assertEquals(products, viewModel.productList.value)
        assertTrue(viewModel.productListState.value.isSuccess)
        assertFalse(viewModel.productListState.value.isLoading)
    }

    @Test
    fun `getProducts failure updates isError state`() = runTest {
        whenever(getProductsUseCase()).thenReturn(Result.failure(Exception("Error")))

        viewModel.getProducts()

        assertTrue(viewModel.productListState.value.isError)
        assertFalse(viewModel.productListState.value.isLoading)
    }

    @Test
    fun `subtractProduct should decrease quantity without going below 0`() {
        val initial =
            listOf(ProductEntity("1", "Test", "", 10.0, "cat", "", 3, false, false, false))

        viewModel.apply {
            productList.value = initial
        }

        viewModel.subtractProduct("1", 2)
        assertEquals(1, viewModel.productList.value.first().quantity)

        viewModel.subtractProduct("1", 5)
        assertEquals(0, viewModel.productList.value.first().quantity)
    }

    @Test
    fun `addProduct should increase quantity correctly`() {
        val initial =
            listOf(ProductEntity("1", "Test", "", 10.0, "cat", "", 1, false, false, false))

        viewModel.apply {
            productList.value = initial
        }

        viewModel.addProduct("1", 3)
        assertEquals(4, viewModel.productList.value.first().quantity)
    }

    @Test
    fun `getProduct should return correct product by id`() {
        val product = ProductEntity("1", "Test", "", 10.0, "cat", "", 1, false, false, false)

        viewModel.apply {
            productList.value = listOf(product)
        }

        val result = viewModel.getProduct("1")
        assertEquals(product, result)
    }

    @Test
    fun `searchProduct with blank should reset to allProducts`() = runTest {
        val products = listOf(
            ProductEntity("1", "Pizza", "", 1000.0, "fast_food", "", 5, true, false, false),
            ProductEntity("2", "Salad", "", 500.0, "salad", "", 10, false, true, false)
        )

        whenever(getProductsUseCase()).thenReturn(Result.success(products))
        viewModel.getProducts()

        advanceUntilIdle()

        viewModel.searchProduct("")
        assertEquals(products, viewModel.productList.value)
    }

    @Test
    fun `searchProduct filters by name ignoring case`() = runTest {
        val products = listOf(
            ProductEntity("1", "Pizza", "", 1000.0, "fast_food", "", 5, true, false, false),
            ProductEntity("2", "Salad", "", 500.0, "salad", "", 10, false, true, false)
        )

        whenever(getProductsUseCase()).thenReturn(Result.success(products))
        viewModel.getProducts()

        advanceUntilIdle()

        viewModel.searchProduct("pizz")

        assertEquals(1, viewModel.productList.value.size)
        assertEquals("Pizza", viewModel.productList.value.first().name)
    }

    @Test
    fun `filterProducts filters by category and dietary correctly`() = runTest {
        val products = listOf(
            ProductEntity("1", "Pizza", "", 1000.0, "fast_food", "", 5, true, false, false),
            ProductEntity("2", "Veggie Salad", "", 500.0, "healthy_food", "", 10, false, true, true)
        )

        val categoryFilters = mapOf("healthy_food" to true)
        val dietaryFilters = mapOf("vegetarian" to true)

        whenever(getProductsUseCase()).thenReturn(Result.success(products))
        viewModel.getProducts()

        advanceUntilIdle()

        viewModel.filterProducts(categoryFilters, dietaryFilters)

        assertEquals(1, viewModel.productList.value.size)
        assertEquals("Veggie Salad", viewModel.productList.value.first().name)
    }
}