package com.example.productFilter

import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class ProductFilterViewModelTest {

    private lateinit var viewModel: ProductFilterViewModel

    @Before
    fun setup() {
        viewModel = ProductFilterViewModel()
    }

    @Test
    fun `initial dietary filters should all be false`() {
        val result = viewModel.dietaryFilters.value
        result.values.forEach { assertFalse(it) }
    }

    @Test
    fun `initial category filters should all be false`() {
        val result = viewModel.categoryFilters.value
        result.values.forEach { assertFalse(it) }
    }

    @Test
    fun `updateDietaryFilter should update single dietary filter`() {
        val key = viewModel.dietaryKeys.first()
        viewModel.updateDietaryFilter(key, true)

        assertTrue(viewModel.dietaryFilters.value[key] == true)
    }

    @Test
    fun `updateCategoryFilter should update single category filter`() {
        val key = viewModel.categoryKeys.first()
        viewModel.updateCategoryFilter(key, true)

        assertTrue(viewModel.categoryFilters.value[key] == true)
    }

    @Test
    fun `updateAllDietaryFilters should replace all dietary filters`() {
        val newFilters = viewModel.dietaryKeys.associateWith { true }
        viewModel.updateAllDietaryFilters(newFilters)

        assertEquals(newFilters, viewModel.dietaryFilters.value)
    }

    @Test
    fun `updateAllCategoryFilters should replace all category filters`() {
        val newFilters = viewModel.categoryKeys.associateWith { true }
        viewModel.updateAllCategoryFilters(newFilters)

        assertEquals(newFilters, viewModel.categoryFilters.value)
    }

    @Test
    fun `updateFilters should update category filters when type is category`() {
        val newFilters = viewModel.categoryKeys.associateWith { true }
        viewModel.updateFilters("category", newFilters)

        assertEquals(newFilters, viewModel.categoryFilters.value)
    }

    @Test
    fun `updateFilters should update dietary filters when type is dietary`() {
        val newFilters = viewModel.dietaryKeys.associateWith { true }
        viewModel.updateFilters("dietary", newFilters)

        assertEquals(newFilters, viewModel.dietaryFilters.value)
    }
}