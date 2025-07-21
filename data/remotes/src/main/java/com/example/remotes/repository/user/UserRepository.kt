package com.example.remotes.repository.user

import com.example.remotes.dtos.user.UserDto
import retrofit2.Response

interface UserRepository {
    suspend fun getUserByEmail(email: String): Response<UserDto>
    suspend fun updateProfile(request: UserDto): Response<UserDto>
}