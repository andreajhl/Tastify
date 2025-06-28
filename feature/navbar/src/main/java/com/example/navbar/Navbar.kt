package com.example.navbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cart.CartViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cart.CartContract
import com.example.cart.CartDrawerContent
import com.example.cart.CartState
import com.example.model.Product
import com.example.productList.ProductListContract
import com.example.productList.ProductListViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navbar(
    title: String,
    cartViewModel: CartContract,
    productListViewModel: ProductListContract,
    content: @Composable () -> Unit
) {
    val cartState by cartViewModel.cart.collectAsState()

    var showCart by remember { mutableStateOf(false) }
    val totalItems = remember(cartState) { cartViewModel.getTotalItems() }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    fun toggleShowCart() { showCart = !showCart }

    Box(Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = title,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menú"
                            )
                        }
                    },
                    actions = {
                        Box {
                            IconButton(onClick = { toggleShowCart() }) {
                                Icon(
                                    imageVector = Icons.Default.ShoppingCart,
                                    contentDescription = "Carrito"
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
            content = { padding ->
                Box(modifier = Modifier.padding(padding)) {
                    content()
                }
            }
        )


        if(showCart) {
            ModalBottomSheet(
                onDismissRequest = { toggleShowCart() },
                sheetState = sheetState
            ) {
                CartDrawerContent(
                    toggleShowCart = { toggleShowCart() },
                    cartItems = cartState.items.values.toList(),
                    cartViewModel = cartViewModel,
                    productListViewModel = productListViewModel
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NavbarPreview() {
    val mockProducts = listOf(
        Product(1, "Hamburguesa", "", 1500.0, true, 2, false, false, "", "fast_food"),
        Product(2, "Ensalada", "", 1200.0, true, 1, true, true, "", "healthy_food")
    )

    val fakeCartViewModel = object : CartContract {
        override val cart: StateFlow<CartState> = MutableStateFlow(
            CartState(items = mockProducts.associateBy { it.id })
        )

        override fun addToCart(product: Product, quantity: Int) {}
        override fun subtractToCart(productId: Int, quantity: Int) {}
        override fun clearCart() {}
        override fun removeItemCart(productId: Int, callback: (Int) -> Unit) {}
        override fun getTotalPrice(): Double = mockProducts.sumOf { it.price * it.quantity }
        override fun getTotalItems(): Int = mockProducts.sumOf { it.quantity }
    }

    val fakeProductListViewModel = object : ProductListContract {
        override val productList: StateFlow<List<Product>> =
            MutableStateFlow(mockProducts)

        override fun subtractProduct(id: Int, count: Int) {}
        override fun addProduct(id: Int, count: Int) {}
        override fun getProduct(id: Int): Product? = mockProducts.find { it.id == id }
        override fun searchProduct(search: String) {}
        override fun getProductByCategory(categories: List<String>) {}
        override fun getProductByDietary(dietary: List<String>) {}
    }

    Navbar(
        title = "Tastify",
        cartViewModel = fakeCartViewModel,
        productListViewModel = fakeProductListViewModel,
        content = {}
    )
}