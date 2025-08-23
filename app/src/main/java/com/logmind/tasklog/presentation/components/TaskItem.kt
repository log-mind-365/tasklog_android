package com.logmind.tasklog.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.logmind.tasklog.domain.entities.Task


@Composable
fun TaskItem(onClick: () -> Unit, onCheck: () -> Unit, item: Task) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = item.isCompleted,
            onCheckedChange = { onCheck() },
        )
        Column(
            modifier = Modifier
                .weight(1.0f)
                .padding(16.dp)
        ) {
            Text(item.title, style = MaterialTheme.typography.titleMedium)
            Text(item.description, style = MaterialTheme.typography.bodyMedium)
        }
        IconButton(
            onClick = { /*TODO*/ }
        ) {
            Icon(Icons.Filled.Delete, contentDescription = "delete")
        }
        OptionButtonWithDropdown()
    }
}

@Composable
fun OptionButtonWithDropdown() {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf("") }
    val options = listOf("Option 1", "Option 2", "Option 3", "Option 4", "Option 5")

    Box {
        IconButton(
            onClick = { expanded = true }
        ) {
            Icon(Icons.Filled.MoreVert, contentDescription = "more menu")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        selectedOptionText = option
                        expanded = false
                    }
                )
            }
        }
    }
}