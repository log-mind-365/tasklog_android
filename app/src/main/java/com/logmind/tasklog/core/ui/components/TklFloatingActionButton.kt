package com.logmind.tasklog.core.ui.components

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.logmind.tasklog.R


@Composable
fun TklFloatingActionButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick
    ) {
        Icon(
            painter = painterResource(R.drawable.outline_add_task_24),
            contentDescription = "add task"
        )
    }
}

