package com.logmind.tasklog.data.models.responses

import com.logmind.tasklog.domain.entities.User

data class AuthResponse(
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String,
    val expiresIn: Long,
    val data: User
)