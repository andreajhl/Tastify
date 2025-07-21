package com.example.productList

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.library.utils.Category
import com.example.library.utils.Dietary
import com.example.library.utils.resolveImageModel
import com.example.theme.AppAndroidTheme

@Composable
fun ProductCard(
    id: String,
    title: String,
    price: Double,
    quantity: Int = 0,
    glutenFree: Boolean = false,
    imageUrl: String,
    category: String,
    addToCart: (String) -> Unit,
) {
    val enable = quantity > 0
    val activeContainer = MaterialTheme.colorScheme.surface
    val inactiveContainer = MaterialTheme.colorScheme.surfaceVariant
    val disabledContainerColor = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)

    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = if (enable) 6.dp else 0.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = if (enable) activeContainer else inactiveContainer
        ),
        modifier = Modifier
            .fillMaxSize()
            .alpha(if (enable) 1f else 0.5f),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(resolveImageModel(imageUrl))
                    .crossfade(true)
                    .build(),
                contentDescription = title,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop,
                onError = { println("Error cargando imagen") }
            )

            if (glutenFree) {
                Image(
                    painter = painterResource(R.drawable.gluten_free),
                    contentDescription = stringResource(Dietary.getLabel("gluten_free")),
                    modifier = Modifier
                        .size(24.dp)
                        .offset(12.dp, 12.dp)
                )
            }

            TextButton(
                onClick = { addToCart(id) },
                modifier = Modifier
                    .size(30.dp)
                    .align(Alignment.BottomEnd)
                    .offset(y = 12.dp, x = -12.dp),
                shape = CircleShape,
                enabled = enable,
                contentPadding = PaddingValues(top = 5.dp),
                colors = ButtonDefaults.textButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContainerColor = disabledContainerColor,
                    disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) {
                Text(
                    text = "+",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface
                )
            }
        }

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = stringResource(Category.getLabel(category)),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSurface
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    ),
                    modifier = Modifier.fillMaxWidth(0.7f),
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = "$$price",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 14.sp),
                    textAlign = TextAlign.End,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductCardScreenPreview() {
    Box(modifier = Modifier
        .padding(50.dp)
        .height(300.dp)){
        ProductCard(
            id = "1",
            title = "Hamburguesa Doble",
            price = 12.99,
            quantity = 0,
            glutenFree = true,
            imageUrl = "https://via.placeholder.com/300", // imagen dummy
            category = "fast_food",
            addToCart = {}
        )

        AppAndroidTheme(darkTheme = true, dynamicColor = false) {
            ProductCard(
                id = "1",
                title = "Hamburguesa Doble",
                price = 12.99,
                quantity = 0,
                glutenFree = true,
                imageUrl = "https://via.placeholder.com/300", // imagen dummy
                category = "fast_food",
                addToCart = {}
            )
        }
    }
}