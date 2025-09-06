package com.logmind.tasklog.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.logmind.tasklog.core.navigation.DrawerContent
import com.logmind.tasklog.core.navigation.Routes
import com.logmind.tasklog.core.navigation.TaskLogNavGraph
import com.logmind.tasklog.core.navigation.TaskLogNavigationActions
import com.logmind.tasklog.core.ui.components.TklBottomSheet
import com.logmind.tasklog.core.ui.components.TklFloatingActionButton
import com.logmind.tasklog.core.ui.components.TklTopAppBar
import com.logmind.tasklog.core.util.BottomSheetContent
import com.logmind.tasklog.core.util.BottomSheetController
import com.logmind.tasklog.core.util.LocalBottomSheetController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskLogApp(
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    navController: NavHostController = rememberNavController(),
    navActions: TaskLogNavigationActions = remember(navController) {
        TaskLogNavigationActions(navController)
    }
) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val coroutineScope = rememberCoroutineScope()
    val isDrawerEnabled = drawerState.isOpen
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: Routes.HOME
    val bottomSheetController = remember { BottomSheetController() }

    fun openDrawer() {
        coroutineScope.launch { drawerState.open() }
    }

    fun closeDrawer() {
        coroutineScope.launch { drawerState.close() }
    }

    Surface(
        color = MaterialTheme.colorScheme.surfaceContainer
    ) {
        CompositionLocalProvider(LocalBottomSheetController provides bottomSheetController) {
            TaskLogApp(
                drawerState = drawerState,
                isDrawerEnabled = isDrawerEnabled,
                currentRoute = currentRoute,
                openDrawer = ::openDrawer,
                closeDrawer = ::closeDrawer,
                navController = navController,
                navActions = navActions
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TaskLogApp(
    drawerState: DrawerState,
    isDrawerEnabled: Boolean,
    currentRoute: String,
    openDrawer: () -> Unit,
    closeDrawer: () -> Unit,
    navController: NavHostController,
    navActions: TaskLogNavigationActions,
) {
    val bottomSheetController = LocalBottomSheetController.current
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = isDrawerEnabled,
        drawerContent = {
            DrawerContent(
                closeDrawer = { closeDrawer() },
                currentRoute = currentRoute,
                navActions = navActions
            )
        }
    ) {
        Scaffold(
            containerColor = Color.Unspecified,
            topBar = {
                TklTopAppBar(
                    openDrawer = { openDrawer() },
                    navigateToProfile = navActions::navigateToProfile
                )
            },
            floatingActionButton = {
                TklFloatingActionButton(
                    onClick = {
                        bottomSheetController.show(BottomSheetContent.AddTask)
                    }
                )
            }
        ) { innerPadding ->
            TaskLogNavGraph(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                navActions = navActions,
            )
        }

        TklBottomSheet()
    }
}