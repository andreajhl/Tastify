package com.example.data.remote.dtos.user

data class UserUpdateDto(
    val email: String,
    val fullName: String,
    val userImageUrl: String?,
    val address: String?,
    val streetNumber: Int?,
    val apartment: String?,
    val floor: Int?,
    val phone: String?,
)
