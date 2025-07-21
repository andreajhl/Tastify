package com.example.productFilter

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.theme.AppAndroidTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductFilter(
    dietaryMap: Map<String, Int>,
    categoryMap: Map<String, Int>,
    dietaryFilters: Map<String, Boolean>,
    categoryFilters: Map<String, Boolean>,
    onSearchChange: (String) -> Unit,
    onFilterChange: (type: String, updated: Map<String, Boolean>) -> Unit,
) {
    var showFilter by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    fun toggleShowFilters() { showFilter = !showFilter }

    Row(
        modifier = Modifier.height(50.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        IconButton(
            onClick = { toggleShowFilters() },
            modifier = Modifier
                .clip(RoundedCornerShape(30.dp))
                .background(MaterialTheme.colorScheme.primary)
                .border(1.dp, Color.Transparent, RoundedCornerShape(30.dp))
        ) {
            Icon(
                imageVector = Icons.Default.Tune,
                modifier = Modifier.size(30.dp),
                contentDescription = "Abrir filtros",
                tint = MaterialTheme.colorScheme.surface
            )
        }

        if (showFilter) {
            ModalBottomSheet(
                onDismissRequest = { toggleShowFilters() },
                sheetState = sheetState,
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                FilterModalScreen(
                    dietaryMap = dietaryMap,
                    categoryMap = categoryMap,
                    dietaryFilters = dietaryFilters,
                    categoryFilters = categoryFilters,
                    onFilterChange = onFilterChange
                )
            }
        }

        InputSearch(onChange = onSearchChange)
    }
}

@Preview(showBackground = true)
@Composable
fun ProductFilterPreview() {
    val dietaryMap = mapOf(
        "vegetarian" to android.R.string.untitled,
        "gluten_free" to android.R.string.untitled
    )
    val categoryMap = mapOf(
        "pizza" to android.R.string.untitled,
        "burger" to android.R.string.untitled
    )
    val dietaryFilters = mapOf(
        "vegetarian" to false,
        "gluten_free" to true
    )
    val categoryFilters = mapOf(
        "pizza" to true,
        "burger" to false
    )

    AppAndroidTheme(darkTheme = true, dynamicColor = false) {
        ProductFilter(
            dietaryMap = dietaryMap,
            categoryMap = categoryMap,
            dietaryFilters = dietaryFilters,
            categoryFilters = categoryFilters,
            onSearchChange = {},
            onFilterChange = { _, _ -> },
        )
    }
}