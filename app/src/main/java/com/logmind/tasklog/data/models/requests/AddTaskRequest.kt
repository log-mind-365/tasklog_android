package com.logmind.tasklog.data.models.requests

data class AddTaskRequest(
    val title: String,
    val description: String,
)