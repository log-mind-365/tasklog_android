package com.logmind.tasklog.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.logmind.tasklog.data.models.requests.AddTaskRequest
import com.logmind.tasklog.domain.entities.Task
import com.logmind.tasklog.domain.usecases.AddTaskUseCase
import com.logmind.tasklog.domain.usecases.GetTasksUseCase
import com.logmind.tasklog.domain.usecases.UpdateTaskStatusUseCase
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
    val taskTitle: String = "",
    val taskDescription: String = "",
    val isExpanded: Boolean = false,
    val isRefreshing: Boolean = false,
    val showBottomSheet: Boolean = false,
    val selectedStatusTab: Int = 0
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    val getTasksUseCase: GetTasksUseCase,
    val addTaskUseCase: AddTaskUseCase,
    val updateTaskStatusUseCase: UpdateTaskStatusUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadTasks()
    }

    fun updateTaskName(value: String) = _uiState.update { it.copy(taskTitle = value) }

    fun updateIsExpanded(value: Boolean) = _uiState.update { it.copy(isExpanded = value) }

    fun updateTaskDescription(value: String) = _uiState.update { it.copy(taskDescription = value) }

    fun updateShowBottomSheet(value: Boolean) = _uiState.update { it.copy(showBottomSheet = value) }

    fun updateSelectedStatusTab(value: Int) = _uiState.update { it.copy(selectedStatusTab = value) }
    
    fun refresh() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            getTasksUseCase()
                .onSuccess { tasks ->
                    _uiState.update { it.copy(items = tasks, isRefreshing = false) }
                }.onFailure { error ->
                    _uiState.update { it.copy(error = error.message, isRefreshing = false) }
                }
        }
    }

    fun loadTasks() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            getTasksUseCase()
                .onSuccess { tasks ->
                    _uiState.update { it.copy(items = tasks, isLoading = false) }
                }.onFailure { error ->
                    _uiState.update { it.copy(error = error.message, isLoading = false) }
                }
        }
    }

    fun saveTask() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            addTaskUseCase(
                AddTaskRequest(
                    title = _uiState.value.taskTitle,
                    description = _uiState.value.taskDescription
                )
            ).onSuccess { task ->
                println("포스팅 성공 : $task")
                loadTasks()
                _uiState.update { it.copy(isLoading = false) }
            }.onFailure { error ->
                println("포스팅 실패 : $error")
                _uiState.update { it.copy(error = error.message, isLoading = false) }
            }
        }
    }

    fun updateTaskStatus(id: Long, isCompleted: Boolean) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            updateTaskStatusUseCase(
                id = id,
                isCompleted = !isCompleted
            ).onSuccess { task ->
                loadTasks()
                _uiState.update { it.copy(isLoading = false) }
            }.onFailure { error ->
                _uiState.update { it.copy(error = error.message, isLoading = false) }
            }
        }
    }
}
