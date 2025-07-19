package com.example.orderHistory

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.theme.AppAndroidTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderCardSkeleton() {
    val cardColor = MaterialTheme.colorScheme.surface
    val skeletonColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
    val secondarySkeletonColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.4f)

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = cardColor
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .height(20.dp)
                    .background(skeletonColor, RoundedCornerShape(4.dp))
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                repeat(3) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .width(60.dp)
                                .height(10.dp)
                                .background(secondarySkeletonColor, RoundedCornerShape(4.dp))
                        )
                        Box(
                            modifier = Modifier
                                .width(40.dp)
                                .height(16.dp)
                                .background(skeletonColor, RoundedCornerShape(4.dp))
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrderCardSkeletonPreview() {
    AppAndroidTheme(darkTheme = true, dynamicColor = false) {
        OrderCardSkeleton()
    }
}