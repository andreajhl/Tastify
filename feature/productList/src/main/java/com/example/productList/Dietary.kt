package com.example.productList

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

object Dietary {
    @Composable
    fun getLabel(dietary: String): String {
        return when (dietary) {
            "vegan" -> stringResource(R.string.vegan)
            "gluten_free" -> stringResource(R.string.gluten_free)
            else -> dietary
        }
    }

    @Composable
    fun getAllLabels(): Map<String, String> {
        return mapOf(
            "vegan" to stringResource(R.string.vegan),
            "gluten_free" to stringResource(R.string.gluten_free)
        )
    }

    fun getKeys(): List<String> {
        return listOf("gluten_free", "vegan")
    }
}