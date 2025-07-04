package com.example.data.remote.repository.image

import android.content.Context
import android.net.Uri

interface ImageRepository {
    suspend fun uploadImage(uri: Uri, context: Context): String?
}