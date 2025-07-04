package com.example.data.remote.dtos.user

data class UserDto(
    val email: String,
    val fullName: String,
    val encryptedPassword: String,
    val userImageUrl: String?,
    val address: String?,
    val streetNumber: Int?,
    val apartment: String?,
    val floor: Int?,
    val phone: String?,
)
