package com.logmind.tasklog.presentation.home.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.logmind.tasklog.data.models.requests.AddTaskRequest
import com.logmind.tasklog.domain.usecases.TaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AddTaskBottomSheetUiState(
    val taskName: String = "",
    val taskDescription: String = "",
    val isExpanded: Boolean = false,
    val isLoading: Boolean = false,
)

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val taskUseCase: TaskUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddTaskBottomSheetUiState())
    val uiState: StateFlow<AddTaskBottomSheetUiState> = _uiState.asStateFlow()

    fun updateTaskName(value: String) = _uiState.update { it.copy(taskName = value) }

    fun updateIsExpanded(value: Boolean) = _uiState.update { it.copy(isExpanded = value) }

    fun updateDescription(value: String) = _uiState.update { it.copy(taskDescription = value) }

    fun onSubmit() = saveTask()

    private fun saveTask() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            taskUseCase.addTask(
                AddTaskRequest(
                    title = _uiState.value.taskName,
                    description = _uiState.value.taskDescription
                )
            )
        }
    }
}