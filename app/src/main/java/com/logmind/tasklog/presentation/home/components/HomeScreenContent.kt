package com.logmind.tasklog.presentation.home.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.logmind.tasklog.core.ui.components.TklLoadingScreen
import com.logmind.tasklog.core.ui.components.TklTaskItem
import com.logmind.tasklog.domain.entities.Task

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
        TklLoadingScreen(isLoading = isLoading || isRefreshing)
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            items(items) { item ->
                TklTaskItem(
                    onClick = { onClick(item.id) },
                    onCheck = { onCheck(item.id, item.isCompleted) },
                    item = item
                )
            }
        }
    }
}
