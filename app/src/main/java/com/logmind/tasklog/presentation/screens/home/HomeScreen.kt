package com.logmind.tasklog.presentation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.logmind.tasklog.core.navigation.TaskLogNavigationActions
import com.logmind.tasklog.presentation.screens.home.components.HomeScreenAddTaskBottomSheet
import com.logmind.tasklog.presentation.screens.home.components.HomeScreenContent
import com.logmind.tasklog.presentation.screens.home.components.HomeScreenFloatingActionButton
import com.logmind.tasklog.presentation.screens.home.components.HomeScreenTabBar
import com.logmind.tasklog.presentation.screens.home.components.HomeScreenTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    openDrawer: () -> Unit,
    navActions: TaskLogNavigationActions,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val sheetState = rememberModalBottomSheetState()

    Scaffold(
        topBar = {
            HomeScreenTopAppBar(openDrawer = openDrawer)
        },
        floatingActionButton = {
            HomeScreenFloatingActionButton(onClick = viewModel::updateShowBottomSheet)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            HomeScreenTabBar(
                selectedFolder = uiState.selectedStatusTab,
                onClick = viewModel::updateSelectedStatusTab
            )
            HomeScreenContent(
                isRefreshing = uiState.isRefreshing,
                refresh = viewModel::refresh,
                isLoading = uiState.isLoading,
                items = uiState.items,
                onClick = navActions::navigateToTaskDetail,
                onCheck = viewModel::updateTaskStatus
            )
            HomeScreenAddTaskBottomSheet(
                sheetState = sheetState,
                showBottomSheet = uiState.showBottomSheet,
                onDismissRequest = { viewModel.updateShowBottomSheet(false) },
                taskNameValue = uiState.taskTitle,
                onTaskNameValueChange = { viewModel.updateTaskName(it) },
                isExpanded = uiState.isExpanded,
                onIsExpandedChange = { viewModel.updateIsExpanded(it) },
                descriptionValue = uiState.taskDescription,
                onDescriptionValueChange = { viewModel.updateTaskDescription(it) },
                onSubmit = viewModel::saveTask
            )
        }
    }
}

