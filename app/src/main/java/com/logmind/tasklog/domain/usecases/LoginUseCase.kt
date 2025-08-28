package com.logmind.tasklog.domain.usecases

import com.logmind.tasklog.data.models.requests.LoginRequest
import com.logmind.tasklog.data.models.responses.AuthResponse
import com.logmind.tasklog.domain.repositories.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<AuthResponse> {
        return authRepository.login(LoginRequest(email, password))
    }
}