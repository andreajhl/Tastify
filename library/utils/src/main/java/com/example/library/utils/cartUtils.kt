package com.example.library.utils

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

fun getCardType(cardNumber: String): String {
    val cleaned = cardNumber.replace(" ", "")
    return when {
        cleaned.startsWith("4") -> "Visa"
        cleaned.startsWith("34") || cleaned.startsWith("37") -> "American Express"
        cleaned.matches(Regex("^5[1-5].*")) -> "MasterCard"
        cleaned.matches(Regex("^2(2[2-9]|[3-6]|7[01]|720).*")) -> "MasterCard"
        cleaned.startsWith("6011") || cleaned.startsWith("65") -> "Discover"
        cleaned.matches(Regex("^35(2[89]|[3-8][0-9]).*")) -> "JCB"
        else -> "Desconocida"
    }
}

fun getCardGradient(type: String): Brush {
    return when (type) {
        "Visa" -> Brush.horizontalGradient(listOf(Color(0xFF1A1F71), Color(0xFF3A53A4)))
        "MasterCard" -> Brush.horizontalGradient(listOf(Color(0xFFFF5F00), Color(0xFFFFA500)))
        "American Express" -> Brush.horizontalGradient(listOf(Color(0xFF2E77BC), Color(0xFF68A4E2)))
        "Discover" -> Brush.horizontalGradient(listOf(Color(0xFF86B8CF), Color(0xFFD6EAF8)))
        "JCB" -> Brush.horizontalGradient(listOf(Color(0xFF007B8F), Color(0xFF00B4D8)))
        else -> Brush.horizontalGradient(listOf(Color(0xFF1E1E1E), Color(0xFF3C3C3C)))
    }
}

fun getCardColor(type: String): Color {
    return when (type) {
        "Visa" -> Color(0xFF121550)
        "MasterCard" -> Color(0xFFAF1303)
        "American Express" -> Color(0xFF22598C)
        "Discover" -> Color(0xFF6790A2)
        "JCB" -> Color(0xFF005462)
        else -> Color(0xFF1E1E1E)
    }
}

fun formatCardNumber(cardNumber: String): String {
    val cleaned = cardNumber.replace(" ", "")
    return if (cleaned.length >= 4) {
        "**** **** **** ${cleaned.takeLast(4)}"
    } else {
        "•••• •••• •••• ••••"
    }
}

val ExpiryDateVisualTransformation = VisualTransformation { text ->
    val trimmed = text.text.filter { it.isDigit() }.take(4)
    val formatted = when {
        trimmed.length >= 3 -> "${trimmed.substring(0, 2)}/${trimmed.substring(2)}"
        else -> trimmed
    }

    val offsetMapping = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int =
            when {
                offset <= 2 -> offset
                offset <= 4 -> offset + 1
                else -> 5
            }

        override fun transformedToOriginal(offset: Int): Int =
            when {
                offset <= 2 -> offset
                offset <= 5 -> offset - 1
                else -> 4
            }
    }

    TransformedText(AnnotatedString(formatted), offsetMapping)
}