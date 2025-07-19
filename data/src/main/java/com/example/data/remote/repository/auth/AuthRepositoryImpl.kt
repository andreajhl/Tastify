package com.example.data.remote.repository.auth

import com.example.data.remote.api.AuthApi
import com.example.data.remote.dtos.auth.AuthDto
import com.example.data.remote.dtos.auth.RegisterDto
import com.example.data.remote.dtos.user.UserLoginDto
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val api: AuthApi) : AuthRepository {
    override suspend fun login(request: AuthDto): Response<UserLoginDto> = api.login(request)

    override suspend fun register(request: RegisterDto): Response<UserLoginDto> = api.register(request)
}