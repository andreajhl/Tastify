package com.example.productFilter

import android.R
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.common.ToggleButtonGroup
import com.example.common.ToggleOption
import com.example.theme.AppAndroidTheme

@Composable
fun FilterSection(
    titleId: Int,
    options: List<ToggleOption>,
    appliedFilters: Map<String, Boolean>,
    onSelectOption: (Map<String, Boolean>) -> Unit
) {
    Text(
        text = stringResource(titleId),
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onSurface
    )

    ToggleButtonGroup(
        options = options,
        selectedMap = appliedFilters,
        onChange = { updatedMap -> onSelectOption(updatedMap) }
    )
}

@Preview(showBackground = true)
@Composable
fun FilterSectionPreview() {
    val options = listOf(
        ToggleOption("vegetarian", "Vegetariano"),
        ToggleOption("gluten_free", "Sin gluten")
    )
    val selectedMap = mapOf(
        "vegetarian" to true,
        "gluten_free" to false
    )

    AppAndroidTheme(darkTheme = true, dynamicColor = false) {
        Column {
            FilterSection(
                titleId = R.string.untitled,
                options = options,
                appliedFilters = selectedMap,
                onSelectOption = {}
            )
        }
    }
}
