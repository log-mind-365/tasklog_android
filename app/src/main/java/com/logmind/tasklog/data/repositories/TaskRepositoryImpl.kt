package com.logmind.tasklog.data.repositories

import com.logmind.tasklog.data.models.requests.AddTaskRequest
import com.logmind.tasklog.data.models.requests.UpdateTaskRequest
import com.logmind.tasklog.data.models.responses.toDomain
import com.logmind.tasklog.data.service.remote.TaskApi
import com.logmind.tasklog.domain.entities.Task
import com.logmind.tasklog.domain.repositories.TaskRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepositoryImpl @Inject constructor(private val taskApi: TaskApi) : TaskRepository {
    override suspend fun getTasks(): Result<List<Task>> {
        return try {
            val response = taskApi.getTasks()
            if (response.isSuccessful) {
                println(response.body())
                val tasks = response.body()?.map { it.toDomain() } ?: emptyList()
                Result.success(tasks)
            } else {
                Result.failure(Exception("Failed to fetch tasks"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getTaskById(id: Long): Result<Task> {
        return try {
            val response = taskApi.getTaskById(id)
            if (response.isSuccessful) {
                val task = response.body()?.toDomain() ?: throw Exception("Task not found")
                Result.success(task)
            } else {
                Result.failure(Exception("Failed to fetch task"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addTask(task: AddTaskRequest): Result<Unit> {
        return try {
            val response = taskApi.addTask(task)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to add task"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateTask(id: Long, task: UpdateTaskRequest): Result<Task> {
        return try {
            val response = taskApi.updateTask(id, task)
            if (response.isSuccessful) {
                val updatedTask = response.body()?.toDomain() ?: throw Exception("Task not found")
                Result.success(updatedTask)
            } else {
                Result.failure(Exception("Failed to update task"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateTaskStatus(id: Long, isCompleted: Boolean): Result<Unit> {
        return try {
            val response = taskApi.updateTaskStatus(id, isCompleted)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to update task status"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }

    }
}