package com.example.productList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.theme.AppAndroidTheme
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer

@Composable
fun ProductCardSkeleton() {
    val highlightColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
    val placeholderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.4f)

    val shimmer = PlaceholderHighlight.shimmer(
        highlightColor = highlightColor
    )

    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier
            .fillMaxSize()
            .alpha(1f),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .placeholder(
                        visible = true,
                        color = placeholderColor,
                        highlight = shimmer
                    )
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .placeholder(
                        visible = true,
                        color = placeholderColor,
                        highlight = shimmer
                    )
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(14.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .placeholder(
                        visible = true,
                        color = placeholderColor,
                        highlight = shimmer
                    )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductItemSkeletonPreview() {
    AppAndroidTheme(darkTheme = false, dynamicColor = false) {
        ProductCardSkeleton()
    }
}