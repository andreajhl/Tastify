package com.example.profile

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.Profile
import com.example.useCase.profile.LoadProfileUseCase
import com.example.useCase.profile.UpdateProfileDataUseCase
import com.example.useCase.profile.UpdateProfilePictureUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface ProfileContract {
    fun loadProfile()
    fun toggleEditMode()
    fun updateProfilePicture(uri: Uri)
    fun updateLoginField(key: String, value: String)
    fun saveProfileChanges()
    val isEditing: Boolean
    val profileData: StateFlow<Profile>
    val profileState: StateFlow<ProfileState>
}

data class ProfileState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: Boolean? = null
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val loadProfileUseCase: LoadProfileUseCase,
    private val updateProfilePictureUseCase: UpdateProfilePictureUseCase,
    private val saveProfileChangesUseCase: UpdateProfileDataUseCase
) : ViewModel(), ProfileContract {
    private val _profileData = MutableStateFlow(Profile())
    override val profileData: StateFlow<Profile> get() = _profileData

    private val _profileState = MutableStateFlow(ProfileState())
    override val profileState: StateFlow<ProfileState> get() = _profileState


    override var isEditing by mutableStateOf(false)

    override fun loadProfile() {
        viewModelScope.launch {
            val result = loadProfileUseCase()

            result.onSuccess { profile ->
                _profileData.value = profile
            }
        }
    }

    override fun toggleEditMode() {
        isEditing = !isEditing

        if(!isEditing) saveProfileChanges()
    }

    override fun updateProfilePicture(uri: Uri) {
        viewModelScope.launch {
            val result = updateProfilePictureUseCase(uri)
            result.onSuccess { url ->
                _profileData.value = _profileData.value.copy(profileImageUri = url)
            }
        }
    }

    override fun saveProfileChanges() {
        _profileState.value = _profileState.value.copy(isLoading = true)

        viewModelScope.launch {
            val result = saveProfileChangesUseCase(_profileData.value)

            if (result.isSuccess) _profileState.value = _profileState.value.copy(isSuccess = true)
            else _profileState.value = _profileState.value.copy(isError = true)

            _profileState.value = _profileState.value.copy(isLoading = false)
        }
    }

    override fun updateLoginField(field: String, value: String) {
        val currentState = _profileData.value
        val updated = when (field) {
            "name" -> currentState.copy(name = value)
            "lastName" -> currentState.copy(lastName = value)
            "address" -> currentState.copy(address = value)
            "streetNumber" -> currentState.copy(streetNumber = value)
            "apartment" -> currentState.copy(apartment = value)
            "floor" -> currentState.copy(floor = value)
            "phone" -> currentState.copy(phone = value)
            else -> currentState
        }
        _profileData.value = updated
    }
}