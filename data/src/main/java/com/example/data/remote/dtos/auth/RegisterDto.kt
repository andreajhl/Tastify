package com.example.data.remote.dtos.auth

data class RegisterDto(val email: String, val fullName: String, val encryptedPassword: String)
