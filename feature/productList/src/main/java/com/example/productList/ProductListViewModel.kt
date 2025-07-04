package com.example.productList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.remote.repository.product.ProductRepository
import com.example.db.entities.ProductEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RequestState(
    val isLoading: Boolean = true,
    val isSuccess: Boolean = false,
    val isError: Boolean = false
)

interface ProductListContract {
    val productList: StateFlow<List<ProductEntity>>
    val state: MutableStateFlow<RequestState>

    fun subtractProduct(id: Int, count: Int)
    fun addProduct(id: Int, count: Int)
    fun getProduct(id: Int): ProductEntity?
    fun searchProduct(search: String)
    fun getProductByCategory(categories: List<String>)
    fun getProductByDietary(dietary: List<String>)
    fun filterProducts(type: String, filters: Map<String, Boolean>)
}

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val repo: ProductRepository
) : ViewModel(), ProductListContract {

    private val _allProducts = MutableStateFlow<List<ProductEntity>>(emptyList())
    private val _productList = MutableStateFlow<List<ProductEntity>>(emptyList())
    override val productList = _productList

    private val _state = MutableStateFlow(RequestState())
    override val state = _state

    fun getProducts() {
        viewModelScope.launch {
            _state.value = RequestState(isLoading = true)

            try {
                val local = repo.getAll()

                if (local.isEmpty()) {
                    val remote = repo.getAllRemote()
                    _allProducts.value = remote
                    _productList.value = remote
                } else {
                    _allProducts.value = local
                    _productList.value = local
                }

                _state.value = _state.value.copy(isSuccess = true)
            } catch (e: Exception) {
                _state.value = _state.value.copy(isError = true)
            } finally {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }

    override fun subtractProduct(id: Int, count: Int) {
        _productList.value = _productList.value.map { product ->
            if (product.id == id) {
                product.copy(quantity = (product.quantity - count).coerceAtLeast(0))
            } else product
        }
    }

    override fun addProduct(id: Int, count: Int) {
        _productList.value = _productList.value.map { product ->
            if (product.id == id) {
                product.copy(quantity = product.quantity + count)
            } else product
        }
    }

    override fun getProduct(id: Int): ProductEntity? {
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

    override fun getProductByCategory(categories: List<String>) {
        _productList.value = _allProducts.value.filter { product ->
            product.category in categories
        }
    }

    override fun getProductByDietary(dietary: List<String>) {
        _productList.value = _allProducts.value.filter { product ->
            dietary.all { restriction ->
                when (restriction) {
                    "gluten_free" -> product.glutenFree
                    "vegetarian" -> product.vegetarian
                    else -> true
                }
            }
        }
    }

    override fun filterProducts(type: String, filters: Map<String, Boolean>) {
        val selectedFilters = filters.filterValues { it }.keys.toList()

        when (type) {
            "category" -> this.getProductByCategory(selectedFilters)
            "dietary" -> this.getProductByDietary(selectedFilters)
            else -> null
        }
    }
}