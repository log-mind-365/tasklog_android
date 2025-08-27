package com.logmind.tasklog.core.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.logmind.tasklog.core.constants.Spacing
import com.logmind.tasklog.presentation.components.DrawerContent
import com.logmind.tasklog.presentation.screens.home.HomeScreen
import com.logmind.tasklog.presentation.screens.taskdetail.TaskDetailScreen
import com.logmind.tasklog.presentation.theme.TaskLogTheme
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

    ModalNavigationDrawer(
        drawerState = drawerState,
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
            composable(
                route = Routes.HOME
            ) {
                HomeScreen(
                    openDrawer = { scope.launch { drawerState.open() } },
                    navActions = navActions,
                )
            }
            composable(
                route = Routes.TASK_DETAIL,
                arguments = listOf(
                    navArgument(NavigationArgs.TASK_ID_ARG) { type = NavType.IntType }
                )
            ) {
                TaskDetailScreen(onBackPressed = { navActions.navigateBack() })
            }
        }
    }
}
