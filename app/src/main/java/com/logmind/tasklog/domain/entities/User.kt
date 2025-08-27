package com.logmind.tasklog.domain.entities

data class User(
    val id: Long,
    val email: String,
    val username: String,
    val profileImage: String? = null,
    val createdAt: String,
    val updatedAt: String? = null
)