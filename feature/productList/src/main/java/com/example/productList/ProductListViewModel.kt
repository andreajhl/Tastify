package com.example.productList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.db.entities.ProductEntity
import com.example.useCase.productList.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProductListState(
    val isLoading: Boolean = true,
    val isSuccess: Boolean = false,
    val isError: Boolean = false
)

interface ProductListContract {
    val productList: StateFlow<List<ProductEntity>>
    val productListState: MutableStateFlow<ProductListState>

    fun subtractProduct(id: String, count: Int)
    fun addProduct(id: String, count: Int)
    fun getProduct(id: String): ProductEntity?
    fun searchProduct(search: String)
    fun filterProducts(categoryFilters: Map<String, Boolean>, dietaryFilters: Map<String, Boolean>)
    fun getProducts()
}

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
): ViewModel(), ProductListContract {
    private val _allProducts = MutableStateFlow<List<ProductEntity>>(emptyList())
    private val _productList = MutableStateFlow<List<ProductEntity>>(emptyList())
    override val productList = _productList

    private val _productListState = MutableStateFlow(ProductListState())
    override val productListState = _productListState

    override fun getProducts() {
        viewModelScope.launch {
            _productListState.value = ProductListState(isLoading = true)

            val result = getProductsUseCase()

            if (result.isSuccess) {
                val products = result.getOrNull() ?: emptyList()

                _allProducts.value = products
                _productList.value = products
                _productListState.value = _productListState.value.copy(isSuccess = true)
            } else {
                _productListState.value = _productListState.value.copy(isError = true)
            }

            _productListState.value = _productListState.value.copy(isLoading = false)
        }
    }

    override fun subtractProduct(id: String, count: Int) {
        val updatedProducts = _productList.value.map { product ->
            if (product.id == id) {
                val newQuantity = (product.quantity - count).coerceAtLeast(0)
                product.copy(quantity = newQuantity)
            } else product


        }
        _productList.value = updatedProducts
    }

    override fun addProduct(id: String, count: Int) {
        val updatedProducts = _productList.value.map { product ->
            if (product.id == id) {
                product.copy(quantity = product.quantity + count)
            } else product


        }
        _productList.value = updatedProducts
    }

    override fun getProduct(id: String): ProductEntity? {
        return _productList.value.find { it.id == id }
    }

    override fun searchProduct(search: String) {
        _productList.value = if (search.isBlank()) {
            _allProducts.value
        } else {
            _allProducts.value.filter { product ->
                product.name.contains(search, ignoreCase = true)
            }
        }
    }

    override fun filterProducts(
        categoryFilters: Map<String, Boolean>,
        dietaryFilters: Map<String, Boolean>
    ) {
        val selectedCategories = categoryFilters.filterValues { it }.keys
        val selectedDietary = dietaryFilters.filterValues { it }.keys

        _productList.value = _allProducts.value.filter { product ->
            val categoryMatches = selectedCategories.isEmpty() || selectedCategories.contains(product.category)

            val dietaryMatches = selectedDietary.isEmpty() || selectedDietary.any { restriction ->
                when (restriction) {
                    "gluten_free" -> product.glutenFree
                    "vegetarian" -> product.vegetarian
                    else -> false
                }
            }

            categoryMatches && dietaryMatches
        }
    }
}
