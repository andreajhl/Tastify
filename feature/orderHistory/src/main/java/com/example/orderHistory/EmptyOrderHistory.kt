package com.example.orderHistory

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.theme.ui.theme.MainColor

@Composable
fun EmptyOrderHistory(
    onGoHome: () -> Unit,
    padding: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.empty_order_history),
            contentDescription = "Empty orders",
            modifier = Modifier
                .size(200.dp)
                .padding(bottom = 36.dp)
        )

        Text(
            text = "You have no orders yet",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Medium),
            color = Color.Gray,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onGoHome,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MainColor,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Go back to home",
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyOrderHistoryPreview() {
    EmptyOrderHistory(
        onGoHome = {},
        padding = PaddingValues(12.dp)
    )
}
