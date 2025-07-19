package com.example.data.remote.api

import com.example.data.remote.dtos.user.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApi {
    @GET("users/{email}")
    suspend fun getUserByEmail(@Path("email") email: String): Response<UserDto>

    @PUT("users/update/{email}")
    suspend fun updateProfile(
        @Path("email") email: String,
        @Body request: UserDto
    ): Response<UserDto>
}