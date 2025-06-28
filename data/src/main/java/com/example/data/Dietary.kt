package com.example.data

object Dietary {
    fun getLabel(dietary: String): Int {
        return when (dietary) {
            "vegan" -> R.string.vegan
            "gluten_free" -> R.string.gluten_free
            else -> R.string.unknown
        }
    }

    fun getAllLabels(): Map<String, Int> {
        return mapOf(
            "vegan" to R.string.vegan,
            "gluten_free" to R.string.gluten_free
        )
    }

    fun getKeys(): List<String> {
        return listOf("gluten_free", "vegan")
    }
}