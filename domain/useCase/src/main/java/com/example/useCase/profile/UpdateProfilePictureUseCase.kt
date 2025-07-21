package com.example.useCase.profile

import android.content.Context
import android.net.Uri
import com.example.remotes.service.CloudinaryService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UpdateProfilePictureUseCase @Inject constructor(
    private val cloudinaryService: CloudinaryService,
    @ApplicationContext private val context: Context
) {
    suspend operator fun invoke(uri: Uri): Result<String> {
        return try {
            val url = cloudinaryService.uploadImageToCloudinary(context, uri)
            if (url != null) Result.success(url)
            else Result.failure(Exception("Upload failed"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}