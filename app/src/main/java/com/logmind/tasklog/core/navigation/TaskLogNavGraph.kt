package com.logmind.tasklog.core.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.logmind.tasklog.presentation.auth.AuthScreen
import com.logmind.tasklog.presentation.home.HomeScreen
import com.logmind.tasklog.presentation.profile.ProfileScreen
import com.logmind.tasklog.presentation.taskdetail.TaskDetailScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskLogNavGraph(
    modifier: Modifier,
    navController: NavHostController,
    startDestination: String = Routes.HOME,
    navActions: TaskLogNavigationActions
) {

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = Routes.HOME) {
            HomeScreen(
                navActions = navActions,
            )
        }
        composable(
            route = Routes.TASK_DETAIL,
            arguments = listOf(navArgument(NavigationArgs.TASK_ID_ARG) {
                type = NavType.IntType
            })
        ) {
            TaskDetailScreen(onBack = { navActions.navigateBack() })
        }
        composable(route = Routes.PROFILE) {
            ProfileScreen(onBack = { navActions.navigateBack() }, navActions = navActions)
        }
        composable(route = Routes.AUTH) {
            AuthScreen(onBack = { navActions.navigateBack() }, onAuthSuccess = {})
        }
    }
}
