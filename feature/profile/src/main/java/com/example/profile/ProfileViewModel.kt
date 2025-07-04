package com.example.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface ProfileContract {
    fun toggleEditMode()
    fun updateLoginField(key: String, value: String)
    val isEditing: Boolean
    val profile: StateFlow<ProfileState>
}

data class ProfileState(
    val name: String = "",
    val lastName: String = "",
    val address: String = "",
    val streetNumber: String = "",
    val apartment: String = "",
    val floor: String = "",
    val phone: String = ""
)

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel(), ProfileContract {

    private val _profile = MutableStateFlow(ProfileState())
    override val profile: StateFlow<ProfileState> get() = _profile

    override var isEditing by mutableStateOf(false)

    override fun toggleEditMode() {
        isEditing = !isEditing
    }

    override fun updateLoginField(field: String, value: String) {
        val currentState = _profile.value
        val updated = when (field) {
            "name" -> currentState.copy(name = value)
            "lastName" -> currentState.copy(lastName = value)
            "address" -> currentState.copy(address = value)
            "streetNumber" -> currentState.copy(streetNumber = value)
            "apartment" -> currentState.copy(apartment = value)
            "floor" -> currentState.copy(apartment = value)
            "phone" -> currentState.copy(phone = value)
            else -> currentState
        }
        _profile.value = updated
    }
}