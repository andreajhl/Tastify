package com.example.data.remote.repository.auth

import com.example.data.remote.api.AuthApi
import com.example.data.remote.dtos.auth.AuthDto
import com.example.data.remote.dtos.auth.LoginDto
import com.example.data.remote.dtos.auth.RegisterDto
import jakarta.inject.Inject
import retrofit2.Response

class AuthRepositoryImpl @Inject constructor(private val api: AuthApi) : AuthRepository {
    override suspend fun login(request: AuthDto): Response<LoginDto> = api.login(request)

    override suspend fun register(request: RegisterDto): Response<LoginDto> = api.register(request)
}