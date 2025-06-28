package com.example.orchestrator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cart.CartViewModel
import com.example.common.Carousel
import com.example.common.CarouselItem
import com.example.productFilter.ProductFilter
import com.example.navbar.Navbar
import com.example.productList.ProductListScreen
import com.example.productList.ProductListViewModel
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Orchestrator() {
    val cartViewModel: CartViewModel = viewModel()
    val productListViewModel: ProductListViewModel = hiltViewModel()

    val productList by productListViewModel.productList.collectAsState()

    val carouselItems = listOf(
        CarouselItem(0, R.drawable.promotion_banner, "promotion banner"),
        CarouselItem(1, R.drawable.membership_banner, "membership banner"),
        CarouselItem(2, R.drawable.supermarket_banner, "supermarket banner"),
    )

    Navbar(
        title = stringResource(R.string.title),
        cartViewModel = cartViewModel,
        productListViewModel = productListViewModel,
    ) {
        Column (verticalArrangement = Arrangement.spacedBy(16.dp)){
            Carousel(carouselItems)

            Column(
                modifier = Modifier.padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ProductFilter()

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

    }
}
