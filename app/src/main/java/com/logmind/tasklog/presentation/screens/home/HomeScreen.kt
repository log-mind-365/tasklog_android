package com.logmind.tasklog.presentation.screens.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.logmind.tasklog.core.navigation.TaskLogNavigationActions
import com.logmind.tasklog.presentation.components.LoadingScreen
import com.logmind.tasklog.presentation.components.TaskItem
import com.logmind.tasklog.presentation.screens.home.components.AddTaskBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    openDrawer: () -> Unit,
    navActions: TaskLogNavigationActions
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { HomeScreenTopAppBar(openDrawer = openDrawer) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showBottomSheet = true }
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "AddTask")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            when (uiState.isLoading) {
                true -> item {
                    LoadingScreen()
                }

                false -> items(uiState.items) { item ->
                    TaskItem(
                        onClick = { navActions.navigateToTaskDetail(item.id) },
                        onCheck = { viewModel.updateTaskStatus(item.id, item.isCompleted) },
                        item = item
                    )
                }
            }
        }

        if (showBottomSheet) {
            AddTaskBottomSheet(
                sheetState = sheetState,
                onDismissRequest = { showBottomSheet = false },
                taskNameValue = uiState.taskTitle,
                onTaskNameValueChange = { viewModel.updateTaskName(it) },
                isExpanded = uiState.isExpanded,
                onIsExpandedChange = { viewModel.updateIsExpanded(it) },
                descriptionValue = uiState.taskDescription,
                onDescriptionValueChange = { viewModel.updateTaskDescription(it) },
                onSubmit = { viewModel.saveTask() }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenTopAppBar(openDrawer: () -> Unit) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
        title = { Text("Home") },
        navigationIcon = {
            IconButton(onClick = openDrawer) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
            }
        }
    )
}