package com.example.productList

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.data.ProductRepository
import com.example.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

interface ProductListContract {
    val productList: StateFlow<List<Product>>

    fun subtractProduct(id: Int, count: Int)
    fun addProduct(id: Int, count: Int)
    fun getProduct(id: Int): Product?
    fun searchProduct(search: String)
    fun getProductByCategory(categories: List<String>)
    fun getProductByDietary(dietary: List<String>)
}

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel(), ProductListContract {

    private val allProducts = productRepository.getMockedProducts()

    private val _productList = MutableStateFlow(allProducts)
    override val productList: StateFlow<List<Product>> get() = _productList

    override fun subtractProduct(id: Int, count: Int) {
        val updatedProducts = _productList.value.map { product ->
            if (product.id == id) {
                val newQuantity = (product.quantity - count).coerceAtLeast(0)
                product.copy(quantity = newQuantity)
            } else product
        }
        _productList.value = updatedProducts
    }

    override fun addProduct(id: Int, count: Int) {
        val updatedProducts = _productList.value.map { product ->
            if (product.id == id) {
                product.copy(quantity = product.quantity + count)
            } else product
        }
        _productList.value = updatedProducts
    }

    override fun getProduct(id: Int): Product? {
        return _productList.value.find { it.id == id }
    }

    override fun searchProduct(search: String) {
        _productList.value = if (search.isBlank()) {
            allProducts
        } else {
            allProducts.filter { product ->
                product.name.contains(search, ignoreCase = true)
            }
        }
    }

    override fun getProductByCategory(categories: List<String>) {
        _productList.value = allProducts.filter { product ->
            product.category in categories
        }
    }

    override fun getProductByDietary(dietary: List<String>) {
        _productList.value = allProducts.filter { product ->
            dietary.all { restriction ->
                when (restriction) {
                    "gluten_free" -> product.glutenFree
                    "vegan" -> product.vegan
                    else -> true
                }
            }
        }
    }
}