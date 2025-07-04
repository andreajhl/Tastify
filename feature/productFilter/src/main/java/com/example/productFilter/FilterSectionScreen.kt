package com.example.productFilter

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.common.ToggleButtonGroup
import com.example.common.ToggleOption

@Composable
fun FilterSectionScreen(
    titleId: Int,
    options:  List<ToggleOption>,
    appliedFilters: Map<String, Boolean>,
    onSelectOption:  (Map<String, Boolean>) -> Unit
) {
    Text(
        text = stringResource(titleId),
        style = MaterialTheme.typography.titleMedium
    )

    ToggleButtonGroup(
        options = options,
        selectedMap = appliedFilters,
        onChange = { onSelectOption }
    )
}

@Preview(showBackground = true)
@Composable
fun FilterSectionScreenPreview() {
    val options = listOf(
        ToggleOption("vegetarian", "Vegetariano"),
        ToggleOption("gluten_free", "Sin gluten")
    )
    val selectedMap = mapOf(
        "vegetarian" to true,
        "gluten_free" to false
    )

    FilterSectionScreen(
        titleId = android.R.string.untitled,
        options = options,
        appliedFilters = selectedMap,
        onSelectOption = {}
    )
}
