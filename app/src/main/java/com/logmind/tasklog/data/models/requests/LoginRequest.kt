package com.logmind.tasklog.data.models.requests

data class LoginRequest(
    val email: String,
    val password: String
)