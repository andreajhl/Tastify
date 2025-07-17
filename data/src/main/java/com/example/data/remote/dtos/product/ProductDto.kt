package com.example.data.remote.dtos.product

data class ProductDto(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val quantity: Int,
    val category: String,
    val imageUrl: String,
    val glutenFree: Boolean,
    val vegetarian: Boolean,
    val hasDrink: Boolean,
)
