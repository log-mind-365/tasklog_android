package com.logmind.tasklog.presentation.screens.taskdetail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlin.collections.listOf

@HiltViewModel
class TaskDetailViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(listOf("asdf"))
    val uiState = _uiState.asStateFlow()
}