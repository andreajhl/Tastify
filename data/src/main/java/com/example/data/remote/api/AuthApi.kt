package com.example.data.remote.api

import com.example.data.remote.dtos.auth.AuthDto
import com.example.data.remote.dtos.auth.RegisterDto
import com.example.data.remote.dtos.user.UserLoginDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("users/login")
    suspend fun login(@Body request: AuthDto): Response<UserLoginDto>

    @POST("users/register")
    suspend fun register(@Body request: RegisterDto): Response<UserLoginDto>
}