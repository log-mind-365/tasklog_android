package com.logmind.tasklog.data.models.responses

import com.logmind.tasklog.domain.entities.Task

data class NetworkTask(
    val id: Long,
    val title: String,
    val description: String,
    val isCompleted: Boolean,
    val createdAt: String,
    val updatedAt: String
)

fun NetworkTask.toDomain(): Task {
    return Task(
        id = id,
        title = title,
        description = description,
        isCompleted = isCompleted,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
