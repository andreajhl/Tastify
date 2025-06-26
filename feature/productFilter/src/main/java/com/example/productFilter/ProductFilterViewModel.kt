package com.example.productFilter

import androidx.lifecycle.ViewModel
import com.example.productList.Category
import com.example.productList.Dietary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class ProductFilterViewModel : ViewModel() {

    val dietaryKeys: List<String> = Dietary.getKeys()
    val categoryKeys: List<String> = Category.getKeys()

    private val initialDietaryFilters = dietaryKeys.associateWith { false }
    private val initialCategoryFilters = categoryKeys.associateWith { false }

    private val _dietaryFilters = MutableStateFlow(initialDietaryFilters)
    val dietaryFilters: StateFlow<Map<String, Boolean>> = _dietaryFilters

    private val _categoryFilters = MutableStateFlow(initialCategoryFilters)
    val categoryFilters: StateFlow<Map<String, Boolean>> = _categoryFilters

    open fun updateDietaryFilter(key: String, isActive: Boolean) {
        _dietaryFilters.value = _dietaryFilters.value.toMutableMap().apply {
            this[key] = isActive
        }
    }

    open fun updateCategoryFilter(key: String, isActive: Boolean) {
        _categoryFilters.value = _categoryFilters.value.toMutableMap().apply {
            this[key] = isActive
        }
    }

    fun updateAllDietaryFilters(newFilters: Map<String, Boolean>) {
        _dietaryFilters.value = newFilters
    }

    fun updateAllCategoryFilters(newFilters: Map<String, Boolean>) {
        _categoryFilters.value = newFilters
    }
}