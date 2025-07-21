package com.example.library.utils

fun isValidPassword(value: String): Boolean {
    val regex =
        Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\$%^&*()_+=\\-{}\\[\\]:;\"'<>,.?/\\\\|`~]).{8,15}$")
    return regex.matches(value)
}

fun isValidEmail(value: String): Boolean {
    val regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
    return regex.matches(value)
}

fun isEquals(firstValue: String, secondValue: String): Boolean {
    return firstValue == secondValue
}

fun isValidText(text: String): Boolean {
    return text.trim().isNotEmpty() && text.trim().length >= 3
}