package com.example.profile

import android.net.Uri
import com.example.model.Profile
import com.example.useCase.profile.LoadProfileUseCase
import com.example.useCase.profile.UpdateProfileDataUseCase
import com.example.useCase.profile.UpdateProfilePictureUseCase
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class ProfileViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val loadProfileUseCase: LoadProfileUseCase = mock()
    private val updateProfilePictureUseCase: UpdateProfilePictureUseCase = mock()
    private val saveProfileChangesUseCase: UpdateProfileDataUseCase = mock()

    private lateinit var viewModel: ProfileViewModel

    @Before
    fun setup() {
        viewModel = ProfileViewModel(
            loadProfileUseCase,
            updateProfilePictureUseCase,
            saveProfileChangesUseCase
        )
    }

    @Test
    fun `loadProfile should update profileData on success`() = runTest {
        val expectedProfile = Profile(name = "Andrea", lastName = "Hernandez")
        whenever(loadProfileUseCase()).thenReturn(Result.success(expectedProfile))

        viewModel.loadProfile()

        advanceUntilIdle()

        assertEquals(expectedProfile, viewModel.profileData.value)
    }

    @Test
    fun `updateProfilePicture should update profileImageUri on success`() = runTest {
        val fakeUri = mock<Uri>()
        val expectedUrl = "https://example.com/photo.jpg"
        whenever(updateProfilePictureUseCase(fakeUri)).thenReturn(Result.success(expectedUrl))

        viewModel.updateProfilePicture(fakeUri)

        advanceUntilIdle()

        assertEquals(expectedUrl, viewModel.profileData.value.profileImageUri)
    }

    @Test
    fun `saveProfileChanges should update profileState to success on success`() = runTest {
        whenever(saveProfileChangesUseCase(any())).thenReturn(Result.success(Unit))

        viewModel.saveProfileChanges()

        advanceUntilIdle()

        assertTrue(viewModel.profileState.value.isSuccess)
    }

    @Test
    fun `saveProfileChanges should update profileState to error on failure`() = runTest {
        whenever(saveProfileChangesUseCase(any())).thenReturn(Result.failure(Exception("Error")))

        viewModel.saveProfileChanges()

        advanceUntilIdle()

        assertTrue(viewModel.profileState.value.isError == true)
    }
}