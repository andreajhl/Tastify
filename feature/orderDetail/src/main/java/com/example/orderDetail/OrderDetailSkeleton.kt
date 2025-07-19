package com.example.orderDetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.theme.AppAndroidTheme
import com.example.theme.DefaultScreenPadding
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer

@Composable
fun OrderDetailSkeleton(
    padding: PaddingValues
) {
    val highlightColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
    val placeholderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.4f)
    val cardColor = MaterialTheme.colorScheme.background

    val shimmer = PlaceholderHighlight.shimmer(
        highlightColor = highlightColor
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(DefaultScreenPadding),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.elevatedCardColors(containerColor = cardColor),
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
                        .clip(RoundedCornerShape(4.dp))
                        .placeholder(
                            visible = true,
                            color = placeholderColor,
                            highlight = shimmer
                        )
                )

                repeat(3) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .height(20.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .placeholder(
                                    visible = true,
                                    color = placeholderColor,
                                    highlight = shimmer
                                )
                        )
                        Box(
                            modifier = Modifier
                                .width(40.dp)
                                .height(20.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .placeholder(
                                    visible = true,
                                    color = placeholderColor,
                                    highlight = shimmer
                                )
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .width(60.dp)
                            .height(20.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .placeholder(
                                visible = true,
                                color = placeholderColor,
                                highlight = shimmer
                            )
                    )
                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .height(20.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .placeholder(
                                visible = true,
                                color = placeholderColor,
                                highlight = shimmer
                            )
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(16.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .placeholder(
                            visible = true,
                            color = placeholderColor,
                            highlight = shimmer
                        )
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun OrderDetailSkeletonPreview() {
    AppAndroidTheme(darkTheme = true, dynamicColor = false) {
        OrderDetailSkeleton(padding = PaddingValues(12.dp))
    }
}