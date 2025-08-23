package com.logmind.tasklog.domain.usecases

import com.logmind.tasklog.domain.repositories.TaskRepository
import javax.inject.Inject

class UpdateTaskStatusUseCase @Inject constructor(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(id: Long, isCompleted: Boolean): Result<Unit> {
        return taskRepository.updateTaskStatus(id, isCompleted)
    }
}