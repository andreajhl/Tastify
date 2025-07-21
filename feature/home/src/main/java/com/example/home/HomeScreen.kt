package com.example.home

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.cart.CartViewModel
import com.example.library.utils.SnackbarManager
import com.example.library.utils.SnackbarType
import com.example.productFilter.ProductFilterViewModel
import com.example.productList.ProductListViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onOpenDrawer: () -> Unit,
    navController: NavHostController
) {
    val context = LocalContext.current

    val cartViewModel: CartViewModel = hiltViewModel()
    val productListViewModel: ProductListViewModel = hiltViewModel()
    val productFilterViewModel: ProductFilterViewModel = hiltViewModel()

    val cartState by cartViewModel.cart.collectAsState()
    val productList by productListViewModel.productList.collectAsState()
    val productListState by productListViewModel.productListState.collectAsState()
    val dietaryFilters = productFilterViewModel.dietaryFilters.collectAsState()
    val categoryFilters = productFilterViewModel.categoryFilters.collectAsState()


    val totalItems = remember(cartState) { cartViewModel.getTotalItems() }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect(Unit) {
        productListViewModel.getProducts()
        cartViewModel.getCart()
    }

    LaunchedEffect(dietaryFilters.value, categoryFilters.value) {
        productListViewModel.filterProducts(categoryFilters.value, dietaryFilters.value)
    }


    LaunchedEffect(productListState) {
        if (productListState.isError == true) {
            SnackbarManager.showMessage(
                actionLabel = context.getString(R.string.product_list_failed_action),
                message = context.getString(R.string.product_list_failed),
                onAction = { productListViewModel.getProducts() },
                type = SnackbarType.ERROR
            )
        }
    }

    HomeContent(
        onOpenDrawer = onOpenDrawer,
        totalItems = totalItems,
        cartState = cartState,
        productList = productList,
        dietaryFilters = dietaryFilters.value,
        categoryFilters = categoryFilters.value,
        onSearchChange = { query -> productListViewModel.searchProduct(query) },
        onFilterChange = { type, updated -> productFilterViewModel.updateFilters(type, updated) },
        onAddToCart = { productId ->
            productListViewModel.subtractProduct(productId, 1)
            productListViewModel.getProduct(productId)?.let { product ->
                cartViewModel.addToCart(product, 1)
            }
        },
        onToggleCart = { cartViewModel.toggleShowCart() },
        sheetState = sheetState,
        navController = navController,
        getProductStock = { productId -> productListViewModel.getProduct(productId)?.quantity ?: 0 },
        onRemoveItemCart = { productId, callback -> cartViewModel.removeItemCart(productId, callback) },
        onSubtractFromCart = { productId, quantity -> cartViewModel.subtractToCart(productId, quantity) },
        onRestoreProduct = { productId, quantity -> productListViewModel.addProduct(productId, quantity) },
        onAddProduct = { productId, quantity -> productListViewModel.subtractProduct(productId, quantity) }
    )
}
