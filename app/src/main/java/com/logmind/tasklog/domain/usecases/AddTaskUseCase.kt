package com.logmind.tasklog.domain.usecases

import com.logmind.tasklog.data.models.requests.AddTaskRequest
import com.logmind.tasklog.domain.repositories.TaskRepository
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(task: AddTaskRequest): Result<Unit> {
        return taskRepository.addTask(task)
    }
}