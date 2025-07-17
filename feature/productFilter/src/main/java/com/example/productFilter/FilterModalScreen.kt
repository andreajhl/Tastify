package com.example.productFilter

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.common.ToggleOption
import kotlin.collections.component1
import kotlin.collections.component2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterModalScreen(
    dietaryMap:  Map<String, Int>,
    categoryMap:  Map<String, Int>,
    dietaryFilters: Map<String, Boolean>,
    categoryFilters: Map<String, Boolean>,
    onFilterChange: (type: String, updated: Map<String, Boolean>) -> Unit,
) {
    val dietaryOptions = dietaryMap.map { (value, label) ->
        ToggleOption(value = value, label = stringResource(label))
    }

    val categoryOptions = categoryMap.map { (value, label) ->
        ToggleOption(value = value, label = stringResource(label))
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(R.string.filter_by),
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )

        FilterSectionScreen(
            titleId = R.string.dietary_preferences_filter,
            options = dietaryOptions,
            appliedFilters = dietaryFilters,
            onSelectOption = { selected ->
                onFilterChange("dietary", selected)
            }
        )

        FilterSectionScreen(
            titleId = R.string.category_filter,
            options = categoryOptions,
            appliedFilters = categoryFilters,
            onSelectOption = { selected ->
                onFilterChange("category", selected)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FilterScreenPreview() {
    val dietaryMap = mapOf(
        "vegetarian" to android.R.string.untitled,
        "gluten_free" to android.R.string.untitled
    )
    val categoryMap = mapOf(
        "international_food" to android.R.string.untitled,
        "fast_food" to android.R.string.untitled
    )
    val dietaryFilters = mapOf(
        "vegetarian" to true,
        "gluten_free" to false
    )
    val categoryFilters = mapOf(
        "international_food" to false,
        "fast_food" to true
    )

    FilterModalScreen(
        dietaryMap = dietaryMap,
        categoryMap = categoryMap,
        dietaryFilters = dietaryFilters,
        categoryFilters = categoryFilters,
        onFilterChange = { _, _ -> }
    )
}
