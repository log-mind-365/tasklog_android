package com.logmind.tasklog.domain.repositories

import com.logmind.tasklog.data.models.requests.JoinRequest
import com.logmind.tasklog.data.models.requests.LoginRequest
import com.logmind.tasklog.data.models.requests.RefreshTokenRequest
import com.logmind.tasklog.data.models.responses.AuthResponse

interface AuthRepository {
    suspend fun join(req: JoinRequest): Result<AuthResponse>
    suspend fun login(req: LoginRequest): Result<AuthResponse>
    suspend fun refreshToken(req: RefreshTokenRequest): Result<AuthResponse>
    suspend fun logout(): Result<Unit>
}