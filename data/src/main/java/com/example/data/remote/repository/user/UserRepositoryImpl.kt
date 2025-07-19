package com.example.data.remote.repository.user

import com.example.data.remote.api.UserApi
import com.example.data.remote.dtos.user.UserDto
import retrofit2.Response
import retrofit2.http.Body
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val api: UserApi) : UserRepository {
    override suspend fun getUserByEmail(email: String): Response<UserDto> =
        api.getUserByEmail(email)

    override suspend fun updateProfile(@Body request: UserDto): Response<UserDto> =
        api.updateProfile(email = request.email, request = request)
}