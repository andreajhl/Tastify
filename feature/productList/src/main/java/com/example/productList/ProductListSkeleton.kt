package com.example.productList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.theme.AppAndroidTheme

@Composable
fun ProductListSkeleton() {
    val skeletonList = List(6) { it }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        items(
            items = skeletonList,
            key = { it }
        ) {
            ProductCardSkeleton()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductListSkeletonPreview() {
    AppAndroidTheme(darkTheme = true, dynamicColor = false) {
        ProductListSkeleton()
    }
}