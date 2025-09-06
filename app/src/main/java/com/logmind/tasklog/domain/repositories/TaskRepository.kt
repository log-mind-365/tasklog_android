package com.logmind.tasklog.domain.repositories

import com.logmind.tasklog.data.models.requests.AddTaskRequest
import com.logmind.tasklog.data.models.requests.UpdateTaskRequest
import com.logmind.tasklog.domain.entities.Task

interface TaskRepository {
    suspend fun getTasks(): Result<List<Task>>

    suspend fun getTaskById(id: Long): Result<Task>

    suspend fun addTask(task: AddTaskRequest): Result<Task>

    suspend fun updateTask(id: Long, task: UpdateTaskRequest): Result<Task>
}