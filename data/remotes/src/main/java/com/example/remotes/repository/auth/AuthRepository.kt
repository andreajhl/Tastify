package com.example.remotes.repository.auth

import com.example.remotes.dtos.auth.AuthDto
import com.example.remotes.dtos.auth.RegisterDto
import com.example.remotes.dtos.user.UserLoginDto
import retrofit2.Response

interface AuthRepository {
    suspend fun login(request: AuthDto): Response<UserLoginDto>
    suspend fun register(request: RegisterDto): Response<UserLoginDto>
}