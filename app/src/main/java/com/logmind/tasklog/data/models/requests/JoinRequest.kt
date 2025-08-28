package com.logmind.tasklog.data.models.requests

data class JoinRequest(
    val email: String,
    val password: String
)