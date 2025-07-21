package com.example.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class CarouselItem(
    val id: Int,
    @DrawableRes val imageResId: Int,
    val contentDescription: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Carousel(
) {
    val carouselItems = listOf(
        CarouselItem(0, R.drawable.promotion_banner, "promotion banner"),
        CarouselItem(1, R.drawable.membership_banner, "membership banner"),
        CarouselItem(2, R.drawable.supermarket_banner, "supermarket banner"),
    )

    HorizontalUncontainedCarousel(
        state = rememberCarouselState { Int.MAX_VALUE },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        itemWidth = 186.dp,
        itemSpacing = 8.dp,
        contentPadding = PaddingValues(end = 16.dp, start = 16.dp, top = 10.dp)
    ) { i ->
        val item = carouselItems[i % carouselItems.size]

        Image(
            modifier = Modifier
                .width(510.dp)
                .maskClip(MaterialTheme.shapes.large),
            painter = painterResource(id = item.imageResId),
            contentDescription = item.contentDescription,
            contentScale = ContentScale.Crop
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CarouselPreview() {
    Carousel()
}