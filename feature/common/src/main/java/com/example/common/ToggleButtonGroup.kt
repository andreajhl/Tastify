package com.example.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.Boolean
import androidx.compose.ui.tooling.preview.Preview
import com.example.theme.ui.theme.AppAndroidTheme

data class ToggleOption(
    val label: String,
    val value: String
)

@Composable
fun ToggleButtonGroup(
    options: List<ToggleOption>,
    selectedMap: Map<String, Boolean>,
    onChange: (Map<String, Boolean>) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(options, key = { it.value }) { option ->
            val isActive = selectedMap[option.value] == true

            ToggleButton(
                isActive = isActive,
                onClick = {
                    val updatedMap = selectedMap.toMutableMap().apply {
                        this[option.value] = !isActive
                    }
                    onChange(updatedMap)
                }
            ) {
                Text(option.label, color = MaterialTheme.colorScheme.surface)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ToggleButtonGroupPreview() {
    val mockOptions = listOf(
        ToggleOption("Fast Food", "fast_food"),
        ToggleOption("Healthy", "healthy_food"),
        ToggleOption("Traditional", "traditional_food"),
        ToggleOption("International", "international_food")
    )

    AppAndroidTheme(darkTheme = false, dynamicColor = false) {
        ToggleButtonGroup(
            options = mockOptions,
            selectedMap = mapOf(),
            onChange = {}
        )
    }
}