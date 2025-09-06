package com.logmind.tasklog.core.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import com.logmind.tasklog.core.util.BottomSheetContent
import com.logmind.tasklog.core.util.BottomSheetState
import com.logmind.tasklog.core.util.LocalBottomSheetController
import com.logmind.tasklog.presentation.home.components.AddTaskBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TklBottomSheet() {
    val controller = LocalBottomSheetController.current

    if (controller.bottomSheetState is BottomSheetState.Shown) {
        ModalBottomSheet(
            onDismissRequest = { controller.hide() },
        ) {
            when (controller.bottomSheetContent) {
                is BottomSheetContent.AddTask -> AddTaskBottomSheet(
                    bottomSheetState = controller.bottomSheetState,
                    closeBottomSheet = { controller.hide() }
                )

                else -> null
            }
        }
    }
}
