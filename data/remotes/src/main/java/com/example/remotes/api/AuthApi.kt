package com.example.remotes.api

import com.example.remotes.dtos.auth.AuthDto
import com.example.remotes.dtos.auth.RegisterDto
import com.example.remotes.dtos.user.UserLoginDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("users/login")
    suspend fun login(@Body request: AuthDto): Response<UserLoginDto>

    @POST("users/register")
    suspend fun register(@Body request: RegisterDto): Response<UserLoginDto>
}