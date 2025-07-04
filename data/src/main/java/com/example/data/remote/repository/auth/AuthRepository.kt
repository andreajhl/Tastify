package com.example.data.remote.repository.auth

import com.example.data.remote.dtos.auth.AuthDto
import com.example.data.remote.dtos.auth.LoginDto
import com.example.data.remote.dtos.auth.RegisterDto
import retrofit2.Response

interface AuthRepository {
    suspend fun login(request: AuthDto): Response<LoginDto>
    suspend fun register(request: RegisterDto): Response<LoginDto>
}