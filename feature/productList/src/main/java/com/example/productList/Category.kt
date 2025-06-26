package com.example.productList

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

object Category {
    @Composable
    fun getLabel(category: String): String {
        return when (category) {
            "fast_food" -> stringResource(R.string.fast_food)
            "healthy_food" -> stringResource(R.string.healthy_food)
            "traditional_food" -> stringResource(R.string.traditional_food)
            "international_food" -> stringResource(R.string.international_food)
            else -> category
        }
    }

    @Composable
    fun getAllLabels(): Map<String, String> {
        return mapOf(
            "fast_food" to stringResource(R.string.fast_food),
            "healthy_food" to stringResource(R.string.healthy_food),
            "traditional_food" to stringResource(R.string.traditional_food),
            "international_food" to stringResource(R.string.international_food)
        )
    }

    fun getKeys(): List<String> {
        return listOf("fast_food", "healthy_food", "traditional_food", "international_food")
    }
}