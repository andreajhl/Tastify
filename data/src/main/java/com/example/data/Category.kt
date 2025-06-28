package com.example.data

object Category {
    fun getLabel(category: String): Int {
        return when (category) {
            "fast_food" -> R.string.fast_food
            "healthy_food" -> R.string.healthy_food
            "traditional_food" -> R.string.traditional_food
            "international_food" -> R.string.international_food
            else -> R.string.unknown
        }
    }

    fun getAllLabels(): Map<String, Int> {
        return mapOf(
            "fast_food" to R.string.fast_food,
            "healthy_food" to R.string.healthy_food,
            "traditional_food" to R.string.traditional_food,
            "international_food" to R.string.international_food
        )
    }

    fun getKeys(): List<String> {
        return listOf("fast_food", "healthy_food", "traditional_food", "international_food")
    }
}