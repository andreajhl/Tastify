package com.example.productList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.db.entities.ProductEntity
import com.example.theme.AppAndroidTheme

@Composable
fun ProductListScreen(
    productListState: ProductListState,
    productList: List<ProductEntity>,
    addToCart: (String) -> Unit
) {
    if (productListState.isLoading) {
        ProductListSkeleton()
        return
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(bottom = 20.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(
            items = productList,
            key = { it.id }
        )  { product ->
            ProductCard(
                id = product.id,
                title = product.name,
                price = product.price,
                quantity = product.quantity,
                glutenFree = product.glutenFree,
                imageUrl = product.imageUrl,
                category = product.category,
                addToCart = addToCart
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductListPreview() {
    val fakeItems = listOf(
        ProductEntity(
            id = "1",
            name = "Falafel",
            description = "Falafel con salsa tahini",
            price = 11.0,
            category = "fast_food",
            imageUrl = "https://www.aceitesdeolivadeespana.com/wp-content/uploads/2021/01/receta-falafel.jpg",
            quantity = 10,
            hasDrink = false,
            glutenFree = true,
            vegetarian = true,
        ),
        ProductEntity(
            id = "2",
            name = "Empanadas",
            description = "Carne, jamón y queso, humita",
            price = 12.0,
            category = "fast_food",
            imageUrl = "https://www.redmutual.com.ar/wp-content/uploads/2024/05/empanadas-1200x751.jpg",
            quantity = 2,
            hasDrink = false,
            glutenFree = false,
            vegetarian = false,
        )
    )

    AppAndroidTheme(darkTheme = true, dynamicColor = false) {
        ProductListScreen(
            productListState = ProductListState(isLoading = false),
            productList = fakeItems,
            addToCart = {}
        )
    }
}