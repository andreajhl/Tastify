package com.example.data.remote.repository.image

import android.content.Context
import android.net.Uri
import com.example.data.remote.service.CloudinaryService
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(private val service: CloudinaryService) :
    ImageRepository {
    override suspend fun uploadImage(
        uri: Uri,
        context: Context
    ): String? {
        return service.uploadImageToCloudinary(context, uri)
    }
}