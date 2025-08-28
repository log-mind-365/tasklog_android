package com.logmind.tasklog.data.repositories

import com.logmind.tasklog.data.models.requests.JoinRequest
import com.logmind.tasklog.data.models.requests.LoginRequest
import com.logmind.tasklog.data.models.requests.RefreshTokenRequest
import com.logmind.tasklog.data.models.responses.AuthResponse
import com.logmind.tasklog.data.remote.AuthApi
import com.logmind.tasklog.domain.repositories.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi
) : AuthRepository {
    
    override suspend fun join(req: JoinRequest): Result<AuthResponse> {
        return runCatching {
            val response = authApi.join(req)
            if (response.isSuccessful && response.body() != null) {
                response.body()!!
            } else {
                throw Exception("Failed to join: ${response.errorBody()?.string()}")
            }
        }
    }

    override suspend fun login(req: LoginRequest): Result<AuthResponse> {
        return runCatching {
            val response = authApi.login(req)
            if (response.isSuccessful && response.body() != null) {
                response.body()!!
            } else {
                throw Exception("Failed to login: ${response.errorBody()?.string()}")
            }
        }
    }

    override suspend fun refreshToken(req: RefreshTokenRequest): Result<AuthResponse> {
        return runCatching {
            val response = authApi.refreshToken(req)
            if (response.isSuccessful && response.body() != null) {
                response.body()!!
            } else {
                throw Exception("Failed to refresh token: ${response.errorBody()?.string()}")
            }
        }
    }

    override suspend fun logout(): Result<Unit> {
        return runCatching {
            val response = authApi.logout()
            if (response.isSuccessful) {
                Unit
            } else {
                throw Exception("Failed to logout: ${response.errorBody()?.string()}")
            }
        }
    }
}