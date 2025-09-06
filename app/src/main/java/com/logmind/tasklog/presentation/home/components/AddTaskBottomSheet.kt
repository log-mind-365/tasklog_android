package com.logmind.tasklog.presentation.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.logmind.tasklog.core.constants.Spacing
import com.logmind.tasklog.core.util.BottomSheetState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskBottomSheet(
    bottomSheetState: BottomSheetState,
    closeBottomSheet: () -> Unit,
    viewModel: AddTaskViewModel = hiltViewModel()
) {
    val titleFocusRequester = remember { FocusRequester() }
    val descriptionFocusRequester = remember { FocusRequester() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(bottomSheetState) {
        if (bottomSheetState is BottomSheetState.Shown) {
            titleFocusRequester.requestFocus()
        }
    }

    LaunchedEffect(uiState.isExpanded) {
        if (uiState.isExpanded) {
            descriptionFocusRequester.requestFocus()
        }
    }

    Column {
        Column(Modifier.padding(horizontal = Spacing.LG.dp)) {
            Text("Add Task")
            Spacer(Modifier.height(Spacing.MD.dp))
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(titleFocusRequester),
                value = uiState.taskName,
                onValueChange = viewModel::updateTaskName,
                label = { Text("Task Name") },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                )
            )
            Spacer(Modifier.height(Spacing.MD.dp))
            AnimatedVisibility(
                visible = uiState.isExpanded
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(descriptionFocusRequester),
                    value = uiState.taskDescription,
                    onValueChange = viewModel::updateDescription,
                    label = { Text("Task Description") }
                )
            }
        }
        Spacer(Modifier.height(Spacing.MD.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = { viewModel.updateIsExpanded(true) }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "AddTask")
            }
            TextButton(
                onClick = {
                    viewModel.onSubmit()
                    closeBottomSheet()
                }
            ) {
                Text("Save")
            }
        }
        Spacer(Modifier.height(Spacing.MD.dp))
    }
}