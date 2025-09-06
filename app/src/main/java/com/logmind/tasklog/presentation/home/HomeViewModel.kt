package com.logmind.tasklog.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.logmind.tasklog.data.models.requests.UpdateTaskRequest
import com.logmind.tasklog.domain.entities.Task
import com.logmind.tasklog.domain.usecases.TaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

val tabMenus = listOf("All", "Completed", "Incomplete")

data class HomeUiState(
    val items: List<Task> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isRefreshing: Boolean = false,
    val selectedStatusTab: Int = 0
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val taskUseCase: TaskUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadTasks()
    }


    fun updateSelectedStatusTab(value: Int) = _uiState.update { it.copy(selectedStatusTab = value) }

    fun refresh() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            taskUseCase.getTasks().let {
                _uiState.update { it.copy(items = it.items, isRefreshing = false) }
            }
        }
    }

    fun loadTasks() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            taskUseCase.getTasks()
                .onSuccess { tasks ->
                    _uiState.update { it.copy(items = tasks, isLoading = false) }
                }.onFailure { error ->
                    _uiState.update { it.copy(error = error.message, isLoading = false) }
                }
        }
    }

    fun updateTaskStatus(id: Long, isCompleted: Boolean) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val currentTask = _uiState.value.items.find { it.id == id } ?: return@launch

            taskUseCase.updateTask(
                id = id,
                task = UpdateTaskRequest(
                    title = currentTask.title,
                    description = currentTask.description,
                    isCompleted = isCompleted
                )
            ).onSuccess { task ->
                loadTasks()
                _uiState.update { it.copy(isLoading = false) }
            }.onFailure { error ->
                _uiState.update { it.copy(error = error.message, isLoading = false) }
            }
        }
    }
}
