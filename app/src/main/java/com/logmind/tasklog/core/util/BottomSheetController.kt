package com.logmind.tasklog.core.util

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

sealed class BottomSheetContent {
    data object AddTask : BottomSheetContent()
    data object EditTask : BottomSheetContent()
    data object DeleteTask : BottomSheetContent()
}

sealed class BottomSheetState {
    data object Hidden : BottomSheetState()
    data class Shown(
        val isDismissible: Boolean = true
    ) : BottomSheetState()
}

class BottomSheetController {
    private var _bottomSheetState: BottomSheetState by mutableStateOf(BottomSheetState.Hidden)
    private var _bottomSheetContent: BottomSheetContent? by mutableStateOf(null)
    val bottomSheetState: BottomSheetState
        get() = _bottomSheetState

    val bottomSheetContent: BottomSheetContent?
        get() = _bottomSheetContent

    fun show(
        content: BottomSheetContent,
        isDismissible: Boolean = true
    ) {
        _bottomSheetState = BottomSheetState.Shown(isDismissible)
        _bottomSheetContent = content
    }

    fun hide() {
        _bottomSheetState = BottomSheetState.Hidden
        _bottomSheetContent = null
    }
}

val LocalBottomSheetController =
    compositionLocalOf<BottomSheetController> { error("BottomSheetController not provided") }