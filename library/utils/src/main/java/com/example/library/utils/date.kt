package com.example.library.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatTimestampToDate(timestamp: Long): String {
    val date = Date(timestamp)
    val formatter = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
    return formatter.format(date)
}