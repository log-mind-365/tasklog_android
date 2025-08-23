package com.logmind.tasklog.domain.entities

data class Task(
    val id: Long,
    val title: String,
    val description: String,
    val isCompleted: Boolean,
    val createdAt: String,
    val updatedAt: String? = null
)

