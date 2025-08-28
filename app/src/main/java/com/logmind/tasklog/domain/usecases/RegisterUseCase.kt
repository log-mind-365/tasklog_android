package com.logmind.tasklog.domain.usecases

import com.logmind.tasklog.data.models.requests.JoinRequest
import com.logmind.tasklog.data.models.responses.AuthResponse
import com.logmind.tasklog.domain.repositories.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<AuthResponse> {
        return authRepository.join(JoinRequest(email, password))
    }
}