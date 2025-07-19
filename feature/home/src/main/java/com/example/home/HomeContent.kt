package com.example.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cart.CartDrawerContent
import com.example.common.Carousel
import com.example.data.Category
import com.example.data.Dietary
import com.example.data.remote.repository.cart.CartState
import com.example.db.entities.ProductEntity
import com.example.navigation.Screen
import com.example.productFilter.ProductFilter
import com.example.productList.ProductListScreen
import com.example.theme.ui.theme.AppAndroidTheme
import com.example.theme.ui.theme.DefaultScreenPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    onOpenDrawer: () -> Unit,
    totalItems: Int,
    cartState: CartState,
    productList: List<ProductEntity>,
    dietaryFilters: Map<String, Boolean>,
    categoryFilters: Map<String, Boolean>,
    onSearchChange: (String) -> Unit,
    onFilterChange: (String, Map<String, Boolean>) -> Unit,
    onAddToCart: (String) -> Unit,
    onToggleCart: () -> Unit,
    sheetState: SheetState,
    navController: NavHostController,
    getProductStock: (String) -> Int,
    onRemoveItemCart: (String, (Int) -> Unit) -> Unit,
    onSubtractFromCart: (String, Int) -> Unit,
    onRestoreProduct: (String, Int) -> Unit,
    onAddProduct: (String, Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.home_title),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onOpenDrawer) {
                        Icon(
                            Icons.Default.Menu,
                            contentDescription = stringResource(R.string.home_menu),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                actions = {
                    Box {
                        IconButton(onClick = onToggleCart) {
                            Icon(
                                Icons.Default.ShoppingCart,
                                contentDescription = stringResource(R.string.home_cart),
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        if (totalItems > 0) {
                            Badge(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .offset(x = (-6).dp, y = 6.dp),
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ) {
                                Text(totalItems.toString())
                            }
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Carousel()
            Column(
                modifier = Modifier.padding(DefaultScreenPadding),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ProductFilter(
                    dietaryFilters = dietaryFilters,
                    categoryFilters = categoryFilters,
                    dietaryMap = Dietary.getAllLabels(),
                    categoryMap = Category.getAllLabels(),
                    onSearchChange = onSearchChange,
                    onFilterChange = onFilterChange
                )
                ProductListScreen(
                    productList,
                    addToCart = onAddToCart
                )
            }
        }
        if (cartState.showCart) {
            ModalBottomSheet(
                onDismissRequest = onToggleCart,
                sheetState = sheetState,
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                CartDrawerContent(
                    totalPrice = cartState.totalPrice,
                    cartItems = cartState.items.values.toList(),
                    toggleShowCart = onToggleCart,
                    goToPay = { navController.navigate(Screen.OrderPay.route) },
                    addToCart = { product, quantity -> onAddToCart(product.id) },
                    getProductStock = getProductStock,
                    removeItemCart = onRemoveItemCart,
                    subtractFromCart = onSubtractFromCart,
                    onRestoreProduct = onRestoreProduct,
                    onAddProduct = onAddProduct
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun HomeContentPreview() {
    val fakeCartState = CartState(
        items = emptyMap(),
        showCart = false,
        totalPrice = 0.0
    )

    val fakeProducts = listOf(
        ProductEntity(
            id = "1",
            name = "Pizza Margherita",
            price = 10.0,
            quantity = 5,
            category = "fast_food",
            glutenFree = false,
            vegetarian = true,
            description = "Salsa de tomate, mozzarella y albahaca fresca.",
            imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTDx46gfGPL3XKmoiXU_pQzvINxjjOFsXLoAA&s",
            hasDrink = false
        ),
        ProductEntity(
            id = "2",
            name = "Hamburguesa clásica",
            price = 8.0,
            quantity = 3,
            category = "fast_food",
            glutenFree = true,
            vegetarian = true,
            description = "Pan artesanal, carne de res, lechuga, tomate y mayonesa.",
            imageUrl = "https://imag.bonviveur.com/hamburguesa-clasica.jpg",
            hasDrink = false
        )
    )

    val fakeDietaryFilters = Dietary.getKeys().associateWith { false }
    val fakeCategoryFilters = Category.getKeys().associateWith { false }

    val fakeSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val fakeNavController = rememberNavController()

    AppAndroidTheme(darkTheme = false, dynamicColor = false) {
        HomeContent(
            onOpenDrawer = {},
            totalItems = 0,
            cartState = fakeCartState,
            productList = fakeProducts,
            dietaryFilters = fakeDietaryFilters,
            categoryFilters = fakeCategoryFilters,
            onSearchChange = {},
            onFilterChange = { _, _ -> },
            onAddToCart = {},
            onToggleCart = {},
            sheetState = fakeSheetState,
            navController = fakeNavController,
            getProductStock = { 5 },
            onRemoveItemCart = { _, _ -> },
            onSubtractFromCart = { _, _ -> },
            onRestoreProduct = { _, _ -> },
            onAddProduct = { _, _ -> }
        )
    }
}