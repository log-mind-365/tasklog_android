package com.logmind.tasklog.presentation.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.logmind.tasklog.R
import com.logmind.tasklog.core.navigation.TaskLogNavigationActions
import com.logmind.tasklog.presentation.home.components.HomeScreenContent
import com.logmind.tasklog.presentation.home.components.HomeScreenTabBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navActions: TaskLogNavigationActions,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .clip(MaterialTheme.shapes.large)
                .padding(dimensionResource(R.dimen.spacing_md))
                .border(1.dp, MaterialTheme.colorScheme.outline, MaterialTheme.shapes.large)
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
        }
    }
}

