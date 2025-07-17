package com.example.orderPay

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.library.utils.formatCardNumber
import com.example.library.utils.getCardColor
import com.example.library.utils.getCardGradient
import com.example.library.utils.getCardType

@Composable
fun CreditCardScreen(
    cardNumber: String,
    ownerName: String,
    expiryDate: String,
    cvv: String,
    isBackVisible: Boolean
) {
    val rotation by animateFloatAsState(targetValue = if (isBackVisible) 180f else 0f)

    val cardType = getCardType(cardNumber)
    val gradient = getCardGradient(cardType)
    val color = getCardColor(cardType)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 12 * density
            }
            .background(brush = gradient, shape = RoundedCornerShape(16.dp))
    ) {
        if (rotation <= 90f) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = cardType,
                    color = Color.White,
                    fontSize = 18.sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = formatCardNumber(cardNumber),
                    color = Color.White,
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = stringResource(R.string.owner_label),
                            color = Color.White, fontSize = 12.sp
                        )
                        Text(
                            text = ownerName.ifBlank { stringResource(R.string.owner_placeholder_label) },
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }

                    Column {
                        Text(
                            text = stringResource(R.string.expiry_label),
                            color = Color.White,
                            fontSize = 12.sp
                        )
                        Text(
                            text = expiryDate.ifBlank { stringResource(R.string.expiry_placeholder_label) },
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 40.dp)
                    .graphicsLayer { rotationY = 180f }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .background(color)
                        .align(Alignment.TopCenter)
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)

                        .padding(20.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = stringResource(R.string.security_code_label),
                        color = Color.White,
                        fontSize = 12.sp
                    )
                    Text(
                        text = cvv.ifBlank { stringResource(R.string.security_code_placeholder_label) },
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreditCardBackPreview() {
    Box(modifier = Modifier.padding(12.dp)) {
        CreditCardScreen(
            cardNumber = "4234 5678 9012 3456",
            ownerName = "Andrea Hernández",
            expiryDate = "12/29",
            cvv = "123",
            isBackVisible = false
        )
    }
}
