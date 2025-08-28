package com.logmind.tasklog.domain.usecases

import com.logmind.tasklog.domain.repositories.TokenRepository
import javax.inject.Inject

class ClearJwtTokenUseCase @Inject constructor(
    private val tokenRepository: TokenRepository
) {
    suspend operator fun invoke() {
        tokenRepository.clearAll()
    }
}