package com.example.library.utils

import android.util.Base64

fun resolveImageModel(imageData: String): Any {
    return if (isBase64Image(imageData)) {
        decodeBase64Image(imageData)
    } else {
        imageData
    }
}

fun isBase64Image(data: String): Boolean {
    return data.startsWith("data:image")
}

fun decodeBase64Image(data: String): ByteArray {
    return try {
        val base64Part = data.substringAfter(",")
        Base64.decode(base64Part, Base64.DEFAULT)
    } catch (e: IllegalArgumentException) {
        ByteArray(0)
    }
}