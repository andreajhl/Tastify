package com.example.productFilter

import androidx.lifecycle.ViewModel
import com.example.data.Category
import com.example.data.Dietary
import com.example.db.entities.ProductEntity
import com.example.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

interface ProductFilterContract {
    val dietaryFilters: StateFlow<Map<String, Boolean>>
    val categoryFilters: StateFlow<Map<String, Boolean>>
    fun updateDietaryFilter(key: String, isActive: Boolean)
    fun updateCategoryFilter(key: String, isActive: Boolean)
    fun updateAllDietaryFilters(newFilters: Map<String, Boolean>)
    fun updateAllCategoryFilters(newFilters: Map<String, Boolean>)
    fun updateFilters(type: String, newFilters: Map<String, Boolean>)
}

@HiltViewModel
class ProductFilterViewModel@Inject constructor() : ViewModel(), ProductFilterContract {
    val dietaryKeys: List<String> = Dietary.getKeys()
    val categoryKeys: List<String> = Category.getKeys()

    private val initialDietaryFilters = dietaryKeys.associateWith { false }
    private val initialCategoryFilters = categoryKeys.associateWith { false }

    private val _dietaryFilters = MutableStateFlow(initialDietaryFilters)
    override val dietaryFilters: StateFlow<Map<String, Boolean>> = _dietaryFilters

    private val _categoryFilters = MutableStateFlow(initialCategoryFilters)
    override val categoryFilters: StateFlow<Map<String, Boolean>> = _categoryFilters

    override fun updateDietaryFilter(key: String, isActive: Boolean) {
        _dietaryFilters.value = _dietaryFilters.value.toMutableMap().apply {
            this[key] = isActive
        }
    }

     override fun updateCategoryFilter(key: String, isActive: Boolean) {
        _categoryFilters.value = _categoryFilters.value.toMutableMap().apply {
            this[key] = isActive
        }
    }

    override fun updateAllDietaryFilters(newFilters: Map<String, Boolean>) {
        _dietaryFilters.value = newFilters
    }

    override fun updateAllCategoryFilters(newFilters: Map<String, Boolean>) {
        _categoryFilters.value = newFilters
    }

    override fun updateFilters(type: String, newFilters: Map<String, Boolean>) {
        when (type) {
            "category" -> this.updateAllDietaryFilters(newFilters)
            "dietary" -> this.updateAllCategoryFilters(newFilters)
            else -> null
        }
    }
}