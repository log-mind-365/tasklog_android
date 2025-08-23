package com.logmind.tasklog.domain.usecases

import com.logmind.tasklog.data.models.requests.UpdateTaskRequest
import com.logmind.tasklog.domain.entities.Task
import com.logmind.tasklog.domain.repositories.TaskRepository
import javax.inject.Inject


class UpdateTaskUseCase @Inject constructor(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(id: Long, task: UpdateTaskRequest): Result<Task> {
        return taskRepository.updateTask(id, task)
    }
}