package com.example.orderHistory

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.theme.AppAndroidTheme
import com.example.theme.DefaultScreenPadding

@Composable
fun OrderHistorySkeleton(
    padding:  PaddingValues
) {
    val skeletonList = List(3) { it }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onPrimary)
            .padding(padding)
            .padding(DefaultScreenPadding),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(
            items = skeletonList,
            key = { it }
        ) { currentOrder ->
            OrderCardSkeleton()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrderHistorySkeletonPreview() {
    AppAndroidTheme(darkTheme = true, dynamicColor = false) {
        OrderHistorySkeleton(padding = PaddingValues(12.dp))
    }
}