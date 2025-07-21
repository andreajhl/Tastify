package com.example.useCase.profile

import com.example.model.Profile
import com.example.remotes.repository.user.UserRepository
import com.example.session.SessionManager
import javax.inject.Inject

class LoadProfileUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val sessionManager: SessionManager
) {
    suspend operator fun invoke(): Result<Profile> {
        return try {
            val email = sessionManager.getUserEmail() ?: return Result.failure(Exception("No email"))

            val response = userRepository.getUserByEmail(email)
            if (response.isSuccessful && response.body() != null) {
                val user = response.body()!!
                val profile = Profile(
                    profileImageUri = user.userImageUrl.orEmpty(),
                    name = user.name.orEmpty(),
                    lastName = user.lastName.orEmpty(),
                    address = user.address.orEmpty(),
                    streetNumber = user.streetNumber?.toString().orEmpty(),
                    apartment = user.apartment.orEmpty(),
                    floor = user.floor?.toString().orEmpty(),
                    phone = user.phone.orEmpty()
                )
                Result.success(profile)
            } else {
                Result.failure(Exception("Failed response"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}