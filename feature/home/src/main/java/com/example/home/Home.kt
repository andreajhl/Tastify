package com.example.home

import android.annotation.SuppressLint
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
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cart.CartDrawerContent
import com.example.cart.CartViewModel
import com.example.common.Carousel
import com.example.common.CarouselItem
import com.example.data.Category
import com.example.data.Dietary
import com.example.db.entities.ProductEntity
import com.example.productFilter.ProductFilter
import com.example.productFilter.ProductFilterViewModel
import com.example.productList.ProductListScreen
import com.example.productList.ProductListViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(onOpenDrawer: () -> Unit) {
    val cartViewModel: CartViewModel = hiltViewModel()
    val productListViewModel: ProductListViewModel = hiltViewModel()
    val productFilterViewModel: ProductFilterViewModel = hiltViewModel()

    val cartState by cartViewModel.cart.collectAsState()
    val productList by productListViewModel.productList.collectAsState()
    val dietaryFilters = productFilterViewModel.dietaryFilters.collectAsState()
    val categoryFilters = productFilterViewModel.categoryFilters.collectAsState()

    val totalItems = remember(cartState) { cartViewModel.getTotalItems() }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect(Unit) {
        productListViewModel.getProducts()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.home_title),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onOpenDrawer) {
                        Icon(
                            Icons.Default.Menu,
                            contentDescription = stringResource(R.string.home_menu)
                        )
                    }
                },
                actions = {
                    Box {
                        IconButton(onClick = { cartViewModel.toggleShowCart() }) {
                            Icon(
                                Icons.Default.ShoppingCart,
                                contentDescription = stringResource(R.string.home_cart)
                            )
                        }

                        if (totalItems > 0) {
                            Badge(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .offset(x = (-6).dp, y = 6.dp)
                            ) {
                                Text(totalItems.toString())
                            }
                        }
                    }
                }
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Carousel()

            Column(
                modifier = Modifier.padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ProductFilter(
                    dietaryFilters = dietaryFilters.value,
                    categoryFilters = categoryFilters.value,
                    dietaryMap = Dietary.getAllLabels(),
                    categoryMap = Category.getAllLabels(),
                    onSearchChange = { query -> productListViewModel.searchProduct(query) },
                    onFilterChange = { type, updated -> productFilterViewModel.updateFilters(type, updated) },
                    onToggleSearch = { type, selectedValues -> productListViewModel.filterProducts(type, selectedValues) },
                )

                ProductListScreen(
                    productList,
                    addToCart = { productId ->
                        productListViewModel.subtractProduct(productId, 1)

                        val product = productListViewModel.getProduct(productId)
                        if (product != null) cartViewModel.addToCart(product, 1)
                    }
                )
            }
        }

        if (cartState.showCart) {
            ModalBottomSheet(
                onDismissRequest = { cartViewModel.toggleShowCart() },
                sheetState = sheetState
            ) {
                CartDrawerContent(
                    totalPrice = cartViewModel.getTotalPrice(),
                    cartItems = cartState.items.values.toList(),
                    toggleShowCart = { cartViewModel.toggleShowCart() },
                    addToCart = { product, quantity -> cartViewModel.addToCart(product, quantity) },
                    getProductStock = { productId -> productListViewModel.getProduct(productId)?.quantity ?: 0},
                    removeItemCart = { productId, callback -> cartViewModel.removeItemCart(productId, callback) },
                    subtractFromCart = { productId, quantity -> cartViewModel.subtractToCart(productId, quantity) },
                    onRestoreProduct = { productId, quantity -> productListViewModel.addProduct(productId, quantity) },
                    onAddProduct = { productId, quantity -> productListViewModel.subtractProduct(productId, quantity) },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    Home(onOpenDrawer = {})
}