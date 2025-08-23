package com.logmind.tasklog.domain.usecases

import com.logmind.tasklog.domain.entities.Task
import com.logmind.tasklog.domain.repositories.TaskRepository
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(): Result<List<Task>> {
        return taskRepository.getTasks()
    }
}