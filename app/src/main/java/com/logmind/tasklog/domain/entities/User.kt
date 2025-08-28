package com.logmind.tasklog.domain.entities

data class User(
    val id: Long,
    val email: String,
    val displayName: String? = null,
    val profileImage: String? = null,
    val createdAt: String,
    val updatedAt: String
)