package com.logmind.tasklog.domain.usecases

import com.logmind.tasklog.domain.repositories.TokenRepository
import javax.inject.Inject

class SaveJwtTokenUseCase @Inject constructor(
    private val tokenRepository: TokenRepository
) {
    suspend operator fun invoke(accessToken: String, refreshToken: String) {
        tokenRepository.saveTokens(accessToken, refreshToken)
    }
}