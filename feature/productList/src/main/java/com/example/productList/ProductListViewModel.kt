package com.example.productList

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.data.ProductRepository
import com.example.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class ProductListViewModel: ViewModel() {
    private val allProducts = ProductRepository.getMockedProducts()

    private val _productList = MutableStateFlow(allProducts)
    open val productList: StateFlow<List<Product>> get() = _productList

    open fun subtractProduct(id: Int, count: Int) {
        val updatedProducts = _productList.value.map { product ->
            if (product.id == id) {
                val newQuantity = (product.quantity - count).coerceAtLeast(0)
                product.copy(quantity = newQuantity)
            } else {
                product
            }
        }
        _productList.value = updatedProducts
    }

    open fun addProduct(id: Int, count: Int) {
        val updatedProducts = _productList.value.map { product ->
            if (product.id == id) {
                product.copy(quantity = product.quantity + count)
            } else {
                product
            }
        }
        _productList.value = updatedProducts
    }

    open fun getProduct(id: Int): Product? {
        return _productList.value.find { it.id == id }
    }

    open fun searchProduct(search: String) {
        if(search.isBlank()) _productList.value = allProducts

        _productList.value = allProducts.filter { product ->
            product.name.contains(search, ignoreCase = true)
        }
    }

    fun getProductByCategory(categories: List<String>) {
        Log.d("categories", categories.toString())
        _productList.value = allProducts.filter { product ->
            product.category in categories
        }
    }

    fun getProductByDietary(dietary: List<String>) {
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