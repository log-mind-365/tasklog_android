package com.logmind.tasklog.domain.repositories

interface TokenRepository {
    suspend fun saveTokens(accessToken: String, refreshToken: String)
    suspend fun getAccessToken(): String?
    suspend fun getRefreshToken(): String?
    suspend fun clearAll()
}