package com.logmind.tasklog.data.repositories

import com.logmind.tasklog.data.local.TokenDataStore
import com.logmind.tasklog.domain.repositories.TokenRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenRepositoryImpl @Inject constructor(
    private val tokenDataStore: TokenDataStore
) : TokenRepository {
    override suspend fun saveTokens(accessToken: String, refreshToken: String) {
        tokenDataStore.saveTokens(accessToken, refreshToken)
    }

    override suspend fun getAccessToken(): String? {
        return tokenDataStore.getAccessToken().first()
    }

    override suspend fun getRefreshToken(): String? {
        return tokenDataStore.getRefreshToken().first()
    }

    override suspend fun clearAll() {
        tokenDataStore.clearAll()
    }


}