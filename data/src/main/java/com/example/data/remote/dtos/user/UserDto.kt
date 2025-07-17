package com.example.data.remote.dtos.user

data class UserDto(
    val id: String,
    val email: String,
    val name: String?,
    val lastName: String?,
    val userImageUrl: String?,
    val address: String?,
    val streetNumber: Int?,
    val apartment: String?,
    val floor: Int?,
    val phone: String?,
)
