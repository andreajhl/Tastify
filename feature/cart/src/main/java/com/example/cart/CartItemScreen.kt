package com.example.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.SwipeToDismissBoxValue.*
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.theme.ui.theme.DisableTextColor
import com.example.theme.ui.theme.MainColor

@Composable
fun CartItemScreen (
    name: String,
    imageUrl: String,
    quantity: Int,
    price: Double,
    enableAddButton: Boolean,
    incrementItem: () -> Unit,
    subtractItem: () -> Unit,
    removeItem: () -> Unit
){
    val swipeState = rememberSwipeToDismissBoxState(
        positionalThreshold = { totalDistance -> totalDistance * 0.1f },
        confirmValueChange = {
            if (it == EndToStart) {
                removeItem()
                true
            } else {
                false
            }
        }
    )

    SwipeToDismissBox(
        state = swipeState,
        enableDismissFromStartToEnd = false,
        modifier = Modifier
            .height(72.dp)
            .padding(vertical = 8.dp)
            .shadow(if (swipeState.dismissDirection == EndToStart) 4.dp else 0.dp),
        backgroundContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        if (swipeState.targetValue == EndToStart) Color.Red
                        else Color.White
                    ),
                contentAlignment = Alignment.CenterEnd
            ) {
                if (swipeState.targetValue == EndToStart) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar item",
                        tint = Color.White,
                        modifier = Modifier
                            .padding(12.dp)
                            .wrapContentSize(Alignment.CenterEnd),
                    )
                }
            }
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = name,
                modifier = Modifier
                    .width(58.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .padding(start = 12.dp),
                contentScale = ContentScale.Crop,
                onError = { println("Error cargando imagen") }
            )

            Column() {
                Text(
                    text = name,
                    modifier = Modifier.width(200.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "$${price * quantity}",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            Row(
                modifier = Modifier
                    .width(162.dp)
                    .fillMaxHeight()
                    .padding(end = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = { subtractItem() },
                    modifier = Modifier
                        .size(18.dp),
                    shape = CircleShape,
                    contentPadding = PaddingValues(bottom = 5.dp),
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = MainColor,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "-",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Text(
                    text = "${quantity}",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .widthIn(min = 24.dp)
                )

                TextButton(
                    onClick = { incrementItem() },
                    modifier = Modifier.size(18.dp),
                    shape = CircleShape,
                    enabled = enableAddButton,
                    contentPadding = PaddingValues(bottom = 5.dp),
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = MainColor,
                        contentColor = Color.White,
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor = DisableTextColor,
                    )
                ) {
                    Text(
                        text = "+",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CartItemPreview() {
    CartItemScreen(
        name = "Falafel",
        price = 1100.0,
        quantity = 10,
        imageUrl = "https://www.aceitesdeolivadeespana.com/wp-content/uploads/2021/01/receta-falafel.jpg",
        incrementItem = {},
        subtractItem = {},
        removeItem = {},
        enableAddButton = false
    )
}