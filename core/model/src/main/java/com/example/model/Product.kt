package com.example.model

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val includesDrink: Boolean,
    var quantity: Int = 0,
    var glutenFree: Boolean = false,
    var vegan: Boolean = false,
    var imageUrl: String,
    var category: String,
)