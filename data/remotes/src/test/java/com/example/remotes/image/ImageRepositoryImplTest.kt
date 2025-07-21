package com.example.remotes.image

import android.content.Context
import android.net.Uri
import com.example.remotes.repository.image.ImageRepositoryImpl
import com.example.remotes.service.CloudinaryService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class ImageRepositoryImplTest {

    private val cloudinaryService: CloudinaryService = mock()
    private lateinit var repository: ImageRepositoryImpl

    @Before
    fun setup() {
        repository = ImageRepositoryImpl(cloudinaryService)
    }

    @Test
    fun `uploadImage should delegate to CloudinaryService and return result`() = runTest {
        val context: Context = mock()
        val uri: Uri = mock()
        val expectedUrl = "https://cloudinary.com/image.jpg"

        whenever(cloudinaryService.uploadImageToCloudinary(context, uri)).thenReturn(expectedUrl)

        val result = repository.uploadImage(uri, context)

        verify(cloudinaryService).uploadImageToCloudinary(context, uri)
        assertEquals(expectedUrl, result)
    }
}