package com.example.profile

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.remote.dtos.user.UserDto
import com.example.data.remote.repository.user.UserRepository
import com.example.data.remote.service.CloudinaryService
import com.example.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

interface ProfileContract {
    fun loadProfile()
    fun toggleEditMode()
    fun updateProfilePicture(uri: Uri)
    fun updateLoginField(key: String, value: String)
    val isEditing: Boolean
    val profile: StateFlow<ProfileState>
}

data class ProfileState(
    val profileImageUri: String = "",
    val name: String = "",
    val lastName: String = "",
    val address: String = "",
    val streetNumber: String = "",
    val apartment: String = "",
    val floor: String = "",
    val phone: String = ""
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    application: Application,
    private val userRepository: UserRepository,
    private val sessionManager: SessionManager,
    private val cloudinaryService: CloudinaryService
) : ViewModel(), ProfileContract {
    private val appContext = application.applicationContext

    private val _profile = MutableStateFlow(ProfileState())
    override val profile: StateFlow<ProfileState> get() = _profile

    override var isEditing by mutableStateOf(false)

    override fun loadProfile() {
        viewModelScope.launch {
            val email = sessionManager.getUserEmail() ?: return@launch

            val response = userRepository.getUserByEmail(email)
            if (response.isSuccessful) {
                val user = response.body()

                user?.let {
                    _profile.value = ProfileState(
                        profileImageUri = it.userImageUrl.orEmpty(),
                        name = it.name.orEmpty(),
                        lastName = it.lastName.orEmpty(),
                        address = it.address.orEmpty(),
                        streetNumber = it.streetNumber?.toString().orEmpty(),
                        apartment = it.apartment.orEmpty(),
                        floor = it.floor?.toString().orEmpty(),
                        phone = it.phone.orEmpty()
                    )
                }
            }
        }
    }

    override fun toggleEditMode() {
        isEditing = !isEditing

        if(!isEditing) saveProfileChanges()
    }

    override fun updateProfilePicture(uri: Uri) {
        viewModelScope.launch {
            val url = cloudinaryService.uploadImageToCloudinary(appContext, uri)

            if (url != null) {
                _profile.value = _profile.value.copy(profileImageUri = url)
            }
        }
    }

    private fun saveProfileChanges() {
        viewModelScope.launch {
            val userId = sessionManager.getUserId() ?: return@launch
            val email = sessionManager.getUserEmail() ?: return@launch

            val dto = UserDto(
                id = userId,
                email = email,
                name = _profile.value.name,
                lastName = _profile.value.lastName,
                userImageUrl = _profile.value.profileImageUri,
                address = _profile.value.address,
                streetNumber = _profile.value.streetNumber.toIntOrNull(),
                apartment = _profile.value.apartment,
                floor = _profile.value.floor.toIntOrNull(),
                phone = _profile.value.phone
            )

            userRepository.updateProfile(dto)
        }
    }

    override fun updateLoginField(field: String, value: String) {
        val currentState = _profile.value
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
        _profile.value = updated
    }
}