package com.logmind.tasklog.domain.usecases

import com.logmind.tasklog.data.models.requests.AddTaskRequest
import com.logmind.tasklog.data.models.requests.UpdateTaskRequest
import com.logmind.tasklog.domain.entities.Task
import com.logmind.tasklog.domain.repositories.TaskRepository
import javax.inject.Inject

class TaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend fun addTask(task: AddTaskRequest): Result<Task> {
        return taskRepository.addTask(task)
    }

    suspend fun getTasks(): Result<List<Task>> {
        return taskRepository.getTasks()
    }

    suspend fun getTaskById(id: Long): Result<Task> {
        return taskRepository.getTaskById(id)
    }

    suspend fun updateTask(id: Long, task: UpdateTaskRequest): Result<Task> {
        return taskRepository.updateTask(id, task)
    }
}