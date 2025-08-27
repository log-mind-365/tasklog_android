package com.logmind.tasklog.navigation

import androidx.navigation.NavHostController

private object NavigationScreens {
    const val HOME_SCREEN = "home"
    const val TASK_DETAIL_SCREEN = "detail"
    const val PROFILE_SCREEN = "profile"
}

object NavigationArgs {
    const val TASK_ID_ARG = "taskId"
}

object Routes {
    const val HOME = NavigationScreens.HOME_SCREEN
    const val TASK_DETAIL =
        "${NavigationScreens.TASK_DETAIL_SCREEN}/{${NavigationArgs.TASK_ID_ARG}}"

    const val PROFILE = NavigationScreens.PROFILE_SCREEN
}

class TaskLogNavigationActions(val navController: NavHostController) {
    fun navigateToHome() {
        navController.navigate(Routes.HOME) {
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToTaskDetail(taskId: Long) {
        navController.navigate("${NavigationScreens.TASK_DETAIL_SCREEN}/$taskId")
    }

    fun navigateToProfile() {
        navController.navigate(NavigationScreens.PROFILE_SCREEN)
    }

    fun navigateBack() {
        navController.popBackStack()
    }
}

