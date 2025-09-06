package com.logmind.tasklog.data.repositories

import com.logmind.tasklog.core.util.execute
import com.logmind.tasklog.data.models.requests.AddTaskRequest
import com.logmind.tasklog.data.models.requests.UpdateTaskRequest
import com.logmind.tasklog.data.models.responses.toDomain
import com.logmind.tasklog.data.remote.TaskApi
import com.logmind.tasklog.domain.entities.Task
import com.logmind.tasklog.domain.repositories.TaskRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepositoryImpl @Inject constructor(private val taskApi: TaskApi) : TaskRepository {
    override suspend fun getTasks(): Result<List<Task>> {
        return execute("getTasks") {
            taskApi.getTasks()
        }.map { responseList -> responseList.map { it.toDomain() } }
    }

    override suspend fun getTaskById(id: Long): Result<Task> {
        return execute("getTaskById") {
            taskApi.getTaskById(id)
        }.map { it.toDomain() }
    }

    override suspend fun addTask(task: AddTaskRequest): Result<Task> {
        return execute("addTask") {
            taskApi.addTask(task)
        }.map { it.toDomain() }
    }

    override suspend fun updateTask(id: Long, task: UpdateTaskRequest): Result<Task> {
        return execute("updateTask") {
            taskApi.updateTask(id, task)
        }.map { it.toDomain() }
    }
}