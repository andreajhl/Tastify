package com.example.data.remote.dtos.auth

import com.example.data.remote.dtos.user.UserDto

data class LoginDto(
    val message: String,
    val user: UserDto
)