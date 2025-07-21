package com.example.useCase.login

import com.example.library.utils.hashPasswordSHA256
import com.example.remotes.dtos.auth.AuthDto
import com.example.remotes.repository.auth.AuthRepository
import com.example.session.SessionManager
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val sessionManager: SessionManager,
) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return try {
            val request = AuthDto(
                email = email,
                encryptedPassword = hashPasswordSHA256(password)
            )

            val response = authRepository.login(request)

            if (response.isSuccessful && response.body() != null) {
                val user = response.body()!!

                sessionManager.setUserId(user.id)
                sessionManager.setUserEmail(user.email)
                sessionManager.setLogged(true)

                Result.success(Unit)
            } else {
                Result.failure(Exception("Login failed: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}