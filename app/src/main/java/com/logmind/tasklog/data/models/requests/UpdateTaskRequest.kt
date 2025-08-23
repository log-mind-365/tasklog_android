package com.logmind.tasklog.data.models.requests

data class UpdateTaskRequest(
    val title: String,
    val description: String,
    val isCompleted: Boolean,
)