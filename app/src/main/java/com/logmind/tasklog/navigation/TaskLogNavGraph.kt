package com.logmind.tasklog.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.logmind.tasklog.presentation.components.DrawerContent
import com.logmind.tasklog.presentation.screens.home.HomeScreen
import com.logmind.tasklog.presentation.screens.profile.ProfileScreen
import com.logmind.tasklog.presentation.screens.taskdetail.TaskDetailScreen
import kotlinx.coroutines.launch

@Composable
fun TaskLogNavGraph(
    navController: NavHostController = rememberNavController(),
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    startDestination: String = Routes.HOME,
    navActions: TaskLogNavigationActions = remember(navController) {
        TaskLogNavigationActions(navController)
    }
) {
    val scope = rememberCoroutineScope()
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: startDestination
    val isDrawerEnabled =
        currentRoute != Routes.TASK_DETAIL && currentRoute != Routes.PROFILE


    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = isDrawerEnabled,
        drawerContent = {
            DrawerContent(
                closeDrawer = { scope.launch { drawerState.close() } },
                currentRoute = currentRoute,
                navActions = navActions
            )
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            composable(route = Routes.HOME) {
                HomeScreen(
                    openDrawer = { scope.launch { drawerState.open() } },
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
                ProfileScreen(onBack = { navActions.navigateBack() })
            }
        }
    }
}
