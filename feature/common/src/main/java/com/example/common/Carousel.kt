package com.example.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.carousel.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
    carouselItems: List<CarouselItem>
) {
    if (carouselItems.isEmpty()) return

    val items = remember { carouselItems }

    HorizontalUncontainedCarousel(
        state = rememberCarouselState { Int.MAX_VALUE },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        itemWidth = 186.dp,
        itemSpacing = 8.dp,
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) { i ->
        val item = items[i % items.size]

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
    val items = listOf(
        CarouselItem(
            id = 3,
            imageResId = R.drawable.ic_launcher_background,
            contentDescription = "Descuentos"
        ),
        CarouselItem(
            id = 1,
            imageResId = R.drawable.ic_launcher_background,
            contentDescription = "Supermercado"
        ),
        CarouselItem(
            id = 2,
            imageResId = R.drawable.ic_launcher_background,
            contentDescription = "Promo Pro"
        ),
    )

    Carousel(carouselItems = items)
}