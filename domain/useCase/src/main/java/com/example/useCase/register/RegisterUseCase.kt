package com.example.useCase.register

import com.example.remotes.dtos.auth.RegisterDto
import com.example.remotes.repository.auth.AuthRepository
import com.example.session.SessionManager
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val sessionManager: SessionManager
) {
    suspend operator fun invoke(request: RegisterDto): Boolean {
        return try {
            val response = authRepository.register(request)
            if (response.isSuccessful && response.body() != null) {
                val user = response.body()!!
                sessionManager.setUserId(user.id)
                sessionManager.setUserEmail(user.email)
                sessionManager.setLogged(true)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }
}