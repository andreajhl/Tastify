package com.example.data.remote.repository.user

import com.example.data.remote.dtos.user.UserDto
import retrofit2.Response

interface UserRepository {
    suspend fun getUserByEmail(email: String): Response<UserDto>
    suspend fun updateProfile(request: UserDto): Response<UserDto>
}