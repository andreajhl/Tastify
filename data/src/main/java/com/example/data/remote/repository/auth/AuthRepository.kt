package com.example.data.remote.repository.auth

import com.example.data.remote.dtos.auth.AuthDto
import com.example.data.remote.dtos.auth.RegisterDto
import com.example.data.remote.dtos.user.UserLoginDto
import retrofit2.Response

interface AuthRepository {
    suspend fun login(request: AuthDto): Response<UserLoginDto>
    suspend fun register(request: RegisterDto): Response<UserLoginDto>
}