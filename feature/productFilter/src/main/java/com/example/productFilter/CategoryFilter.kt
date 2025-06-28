package com.example.productFilter

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.common.ToggleButtonGroup
import com.example.common.ToggleOption
import com.example.data.Category
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun CategoryFilter(productFilterViewModel: ProductFilterContract) {
    val categoryFilters by productFilterViewModel.categoryFilters.collectAsState()
    val categoryMap = Category.getAllLabels()

    val options = categoryMap.map { (value, label) ->
        ToggleOption(value = value, label = stringResource(label))
    }

    Text(
        text = stringResource(R.string.category_filter),
        style = MaterialTheme.typography.titleMedium
    )

    ToggleButtonGroup(
        options = options,
        selectedMap = categoryFilters,
        onChange = { updated ->
            updated.forEach { (key, value) ->
                productFilterViewModel.updateCategoryFilter(key, value)
            }
        }
    )
}
