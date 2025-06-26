package com.example.productFilter

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.common.ToggleButtonGroup
import com.example.common.ToggleOption
import com.example.productList.Dietary

@Composable
fun DietaryFilter(
    productFilterViewModel: ProductFilterViewModel
) {
    val dietaryFilters by productFilterViewModel.dietaryFilters.collectAsState()
    val dietaryMap = Dietary.getAllLabels()

    val options = dietaryMap.map { (value, label) ->
        ToggleOption(value = value, label = label)
    }

    Text(
        text = stringResource(R.string.dietary_preferences_filter),
        style = MaterialTheme.typography.titleMedium
    )

    ToggleButtonGroup(
        options = options,
        selectedMap = dietaryFilters,
        onChange = { updated ->
            updated.forEach { (key, value) ->
                productFilterViewModel.updateDietaryFilter(key, value)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DietaryFilterPreview() {
    val fakeProductFilterViewModel = object : ProductFilterViewModel() {
       override fun updateDietaryFilter(key: String, isActive: Boolean) {}
    }

    DietaryFilter(
        productFilterViewModel = fakeProductFilterViewModel
    )
}