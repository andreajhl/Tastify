package com.example.remotes.dtos.auth

data class RegisterDto(val email: String, val name: String, val lastName: String, val encryptedPassword: String)
