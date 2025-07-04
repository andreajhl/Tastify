package com.example.productFilter

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.productList.ProductListViewModel
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.theme.ui.theme.MainColor
import kotlinx.coroutines.FlowPreview
import kotlin.String

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductFilter(
    dietaryMap: Map<String, Int>,
    categoryMap: Map<String, Int>,
    dietaryFilters: Map<String, Boolean>,
    categoryFilters: Map<String, Boolean>,
    onSearchChange: (String) -> Unit,
    onFilterChange: (type: String, updated: Map<String, Boolean>) -> Unit,
    onToggleSearch: (type: String, selectedValues: Map<String, Boolean>) -> Unit,
) {
    var showFilter by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    fun toggleShowFilters() { showFilter = !showFilter }

    LaunchedEffect(dietaryFilters, categoryFilters) {
        onToggleSearch("category", dietaryFilters)
        onToggleSearch("dietary", categoryFilters)
    }

    Row(
        modifier = Modifier.height(50.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        IconButton(
            onClick = { toggleShowFilters() },
            modifier = Modifier
                .clip(RoundedCornerShape(30.dp))
                .background(MainColor)
                .border(1.dp, Color.Transparent, RoundedCornerShape(30.dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.filter_icon),
                contentDescription = "Abrir filtros"
            )
        }

        if (showFilter) {
            ModalBottomSheet(
                onDismissRequest = { toggleShowFilters() },
                sheetState = sheetState
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

        InputSearchScreen(onChange = onSearchChange)
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

    ProductFilter(
        dietaryMap = dietaryMap,
        categoryMap = categoryMap,
        dietaryFilters = dietaryFilters,
        categoryFilters = categoryFilters,
        onSearchChange = {},
        onFilterChange = { _, _ -> },
        onToggleSearch = { _, _ -> }
    )
}