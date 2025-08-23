package com.logmind.tasklog.presentation.screens.home.components

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.logmind.tasklog.R


@Composable
fun HomeScreenFloatingActionButton(onClick: (Boolean) -> Unit) {
    FloatingActionButton(
        onClick = { onClick(true) }
    ) {
        Icon(
            painter = painterResource(R.drawable.outline_add_task_24),
            contentDescription = "add task"
        )
    }
}

