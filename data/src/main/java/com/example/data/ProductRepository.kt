package com.example.data

import com.example.model.Product


object ProductRepository {
    fun getMockedProducts(): List<Product> {
        return listOf(
            Product(
                id = 1,
                name = "Hamburger Clásica",
                description = "Con cheddar y lechuga",
                price = 15.0,
                includesDrink = true,
                quantity = 0,
                imageUrl = "https://images.unsplash.com/photo-1550547660-d9450f859349",
                category = "fast_food"
            ),
            Product(
                id = 2,
                name = "Pizza de Muzzarella",
                description = "Con orégano y aceitunas",
                price = 2000.0,
                includesDrink = false,
                quantity = 8,
                imageUrl = "https://images.unsplash.com/photo-1601924571486-df53e3e9c5c6",
                category = "fast_food"
            ),
            Product(
                id = 3,
                name = "Ensalada César",
                description = "Lechuga, pollo y croutons",
                price = 18.0,
                includesDrink = true,
                quantity = 7,
                imageUrl = "https://images.unsplash.com/photo-1568605114967-8130f3a36994",
                category = "healthy_food"
            ),
            Product(
                id = 4,
                name = "Empanadas",
                description = "Carne, jamón y queso, humita",
                price = 12.0,
                includesDrink = false,
                quantity = 2,
                imageUrl = "https://www.redmutual.com.ar/wp-content/uploads/2024/05/empanadas-1200x751.jpg",
                category = "traditional_food"
            ),
            Product(
                id = 5,
                name = "Falafel",
                description = "",
                price = 11.0,
                includesDrink = false,
                quantity = 10,
                glutenFree = true,
                imageUrl = "https://www.aceitesdeolivadeespana.com/wp-content/uploads/2021/01/receta-falafel.jpg",
                category = "international_food"
            )
        )
    }
}