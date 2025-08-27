package com.logmind.tasklog.presentation.screens.home.components

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
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.logmind.tasklog.core.constants.Spacing
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenAddTaskBottomSheet(
    modifier: Modifier = Modifier,
    showBottomSheet: Boolean,
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    taskNameValue: String,
    onTaskNameValueChange: (String) -> Unit,
    descriptionValue: String,
    onDescriptionValueChange: (String) -> Unit,
    isExpanded: Boolean,
    onIsExpandedChange: (Boolean) -> Unit,
    onSubmit: () -> Unit
) {
    val titleFocusRequester = remember { FocusRequester() }
    val descriptionFocusRequester = remember { FocusRequester() }

    LaunchedEffect(sheetState.isVisible) {
        if (sheetState.isVisible) {
            delay(100)
            titleFocusRequester.requestFocus()
        }
    }

    LaunchedEffect(isExpanded) {
        if (isExpanded) {
            delay(100)
            descriptionFocusRequester.requestFocus()
        }
    }

    if (showBottomSheet)
        ModalBottomSheet(
            modifier = modifier,
            sheetState = sheetState,
            onDismissRequest = onDismissRequest,
        ) {
            Column(Modifier.padding(horizontal = Spacing.LG.dp)) {
                Text("Add Task")
                Spacer(Modifier.height(Spacing.MD.dp))
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(titleFocusRequester),
                    value = taskNameValue,
                    onValueChange = onTaskNameValueChange,
                    label = { Text("Task Name") },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )
                Spacer(Modifier.height(Spacing.MD.dp))
                AnimatedVisibility(
                    visible = isExpanded
                ) {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(descriptionFocusRequester),
                        value = descriptionValue,
                        onValueChange = onDescriptionValueChange,
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
                    onClick = { onIsExpandedChange(true) }
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "AddTask")
                }
                TextButton(
                    onClick = {
                        onSubmit()
                        onDismissRequest()
                    }
                ) {
                    Text("Save")
                }
            }
            Spacer(Modifier.height(Spacing.MD.dp))
        }
}