package com.example.productFilter

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterScreen(productFilterViewModel: ProductFilterContract) {
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

        CategoryFilter(productFilterViewModel = productFilterViewModel)
        DietaryFilter(productFilterViewModel = productFilterViewModel)
    }
}


@Preview(showBackground = true)
@Composable
fun FilterScreenPreview() {
    val mock = object : ProductFilterContract {
        override val dietaryFilters = MutableStateFlow(mapOf("vegan" to true))
        override val categoryFilters = MutableStateFlow(mapOf("fast_food" to true))
        override fun updateDietaryFilter(key: String, isActive: Boolean) {}
        override fun updateCategoryFilter(key: String, isActive: Boolean) {}
    }

    FilterScreen(productFilterViewModel = mock)
}
