package com.example.orderDetail

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.library.utils.SnackbarManager
import com.example.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailScreen(
    navController: NavHostController,
    orderId: String,
) {
    val context = LocalContext.current

    val orderDetailViewModel: OrderDetailViewModel = hiltViewModel()
    val orderData by orderDetailViewModel.orderDetailData.collectAsState()
    val orderState by orderDetailViewModel.orderDetailState.collectAsState()

    LaunchedEffect(orderId) {
        orderDetailViewModel.getOrderDetail(orderId)
    }

    LaunchedEffect(orderState) {
        if (orderState.isError == true) {
            SnackbarManager.showMessage(
                actionLabel = context.getString(R.string.order_detail_failed_action),
                message = context.getString(R.string.order_detail_failed),
                onAction = { orderDetailViewModel.getOrderDetail(orderId) }
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.order_detail_title),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(Screen.OrderHistory.route)
                    }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.order_detail_comeback)
                        )
                    }
                },
            )
        }
    ) { padding ->
        if (orderState.isLoading || orderData == null) {
            OrderDetailSkeleton(padding = padding)
        } else {
            OrderDetailContent(
                order = orderData!!,
                padding = padding
            )
        }
    }
}
