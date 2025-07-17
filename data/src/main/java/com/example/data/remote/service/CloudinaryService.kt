package com.example.data.remote.service

import android.content.*
import android.graphics.Bitmap
import android.net.*
import android.util.*
import kotlinx.coroutines.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.cloudinary.json.*
import java.io.File
import java.io.FileOutputStream
import javax.inject.*

class CloudinaryService @Inject constructor() {
    private val cloudName = "drioaxhhw"
    private val uploadPreset = "unsigned_android"
    private val uploadUrl = "https://api.cloudinary.com/v1_1/$cloudName/image/upload"
    private val client = OkHttpClient()

    suspend fun uploadImageToCloudinary(context: Context, uri: Uri): String? {
        val inputStream = context.contentResolver.openInputStream(uri)
        val bytes = inputStream?.readBytes() ?: return null

        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(
                "file", "image.jpg",
                bytes.toRequestBody("image/*".toMediaTypeOrNull())
            )
            .addFormDataPart("upload_preset", uploadPreset)
            .build()

        val request = Request.Builder()
            .url(uploadUrl)
            .post(requestBody)
            .build()

        return withContext(Dispatchers.IO) {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val json = JSONObject(response.body?.string() ?: "")
                json.getString("secure_url")
            } else {
                Log.e("CloudinaryService", "Error al subir: ${response.code}")
                null
            }
        }
    }
}