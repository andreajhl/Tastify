package com.example.data

object Dietary {
    fun getLabel(dietary: String): Int {
        return when (dietary) {
            "vegetarian" -> R.string.vegetarian
            "gluten_free" -> R.string.gluten_free
            else -> R.string.unknown
        }
    }

    fun getAllLabels(): Map<String, Int> {
        return mapOf(
            "vegetarian" to R.string.vegetarian,
            "gluten_free" to R.string.gluten_free
        )
    }

    fun getKeys(): List<String> {
        return listOf("gluten_free", "vegetarian")
    }
}