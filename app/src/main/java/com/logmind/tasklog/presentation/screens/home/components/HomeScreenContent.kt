package com.logmind.tasklog.presentation.screens.home.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.logmind.tasklog.domain.entities.Task
import com.logmind.tasklog.presentation.components.LoadingScreen
import com.logmind.tasklog.presentation.components.TaskItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    isRefreshing: Boolean,
    refresh: () -> Unit,
    isLoading: Boolean,
    items: List<Task>,
    onClick: (Long) -> Unit,
    onCheck: (Long, Boolean) -> Unit
) {
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = { refresh() },
    ) {
        LoadingScreen(isLoading = isLoading || isRefreshing)
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            items(items) { item ->
                TaskItem(
                    onClick = { onClick(item.id) },
                    onCheck = { onCheck(item.id, item.isCompleted) },
                    item = item
                )
            }
        }
    }
}
