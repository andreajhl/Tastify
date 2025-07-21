package com.example.useCase.profile

import com.example.model.Profile
import com.example.remotes.dtos.user.UserDto
import com.example.remotes.repository.user.UserRepository
import com.example.session.SessionManager
import javax.inject.Inject

class UpdateProfileDataUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val sessionManager: SessionManager
) {
    suspend operator fun invoke(profile: Profile): Result<Unit> {
        return try {
            val userId = sessionManager.getUserId() ?: return Result.failure(Exception("No user id"))
            val email = sessionManager.getUserEmail() ?: return Result.failure(Exception("No email"))

            val dto = UserDto(
                id = userId,
                email = email,
                name = profile.name,
                lastName = profile.lastName,
                userImageUrl = profile.profileImageUri,
                address = profile.address,
                streetNumber = profile.streetNumber.toIntOrNull(),
                apartment = profile.apartment,
                floor = profile.floor.toIntOrNull(),
                phone = profile.phone
            )

            userRepository.updateProfile(dto)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}