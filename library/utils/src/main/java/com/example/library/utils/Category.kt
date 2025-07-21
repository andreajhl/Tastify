package com.example.library.utils

object Category {
    fun getLabel(category: String): Int {
        return when (category) {
            "fast_food" -> R.string.fast_food
            "healthy_food" -> R.string.healthy_food
            "international_food" -> R.string.international_food
            else -> R.string.unknown
        }
    }

    fun getAllLabels(): Map<String, Int> {
        return mapOf(
            "fast_food" to R.string.fast_food,
            "healthy_food" to R.string.healthy_food,
            "international_food" to R.string.international_food
        )
    }

    fun getKeys(): List<String> {
        return listOf("fast_food", "healthy_food", "international_food")
    }
}