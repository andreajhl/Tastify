package com.example.remotes.repository.auth

import com.example.remotes.api.AuthApi
import com.example.remotes.dtos.auth.AuthDto
import com.example.remotes.dtos.auth.RegisterDto
import com.example.remotes.dtos.user.UserLoginDto
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val api: AuthApi) : AuthRepository {
    override suspend fun login(request: AuthDto): Response<UserLoginDto> = api.login(request)
    override suspend fun register(request: RegisterDto): Response<UserLoginDto> =
        api.register(request)
}