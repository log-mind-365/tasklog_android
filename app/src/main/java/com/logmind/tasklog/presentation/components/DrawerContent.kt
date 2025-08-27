package com.logmind.tasklog.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.logmind.tasklog.core.constants.Spacing
import com.logmind.tasklog.navigation.Routes
import com.logmind.tasklog.navigation.TaskLogNavigationActions
import com.logmind.tasklog.presentation.theme.TaskLogTheme


@Composable
fun DrawerContent(
    closeDrawer: () -> Unit,
    currentRoute: String,
    navActions: TaskLogNavigationActions
) {
    ModalDrawerSheet(Modifier.fillMaxWidth(fraction = 0.8f)) {
        Column(
            modifier = Modifier.padding(vertical = Spacing.MD.dp),
            verticalArrangement = Arrangement.Center
        ) {
            DrawerHeader(closeDrawer = closeDrawer)
            Column(
                modifier = Modifier.padding(horizontal = Spacing.MD.dp),
                verticalArrangement = Arrangement.Center
            ) {

                DrawerButton(
                    onClick = {
                        navActions.navigateToHome()
                        closeDrawer()
                    },
                    isSelected = currentRoute == Routes.HOME,
                    icon = Icons.Default.Home,
                    text = "Home"
                )
                DrawerButton(
                    onClick = {
                        navActions.navigateToTaskDetail(1)
                        closeDrawer()
                    },
                    isSelected = currentRoute == Routes.TASK_DETAIL,
                    icon = Icons.Default.Build,
                    text = "TaskDetail"
                )
            }
        }
    }
}

@Composable
private fun DrawerHeader(closeDrawer: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(start = Spacing.MD.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "TaskLog",
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.headlineSmall
        )
        IconButton(onClick = closeDrawer) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
        }
    }
}

@Composable
private fun DrawerButton(
    onClick: () -> Unit,
    isSelected: Boolean,
    icon: ImageVector,
    text: String
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = when (isSelected) {
                true -> MaterialTheme.colorScheme.primary
                false -> Color.Transparent
            },
            contentColor = when (isSelected) {
                true -> Color.White
                false -> MaterialTheme.colorScheme.onSurface
            }
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(imageVector = icon, contentDescription = "Close")
            Spacer(Modifier.width(Spacing.MD.dp))
            Text(text = text, style = MaterialTheme.typography.bodyLarge)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DrawerContentPreview() {
    TaskLogTheme {
        DrawerContent(
            closeDrawer = {},
            currentRoute = Routes.HOME,
            navActions = TaskLogNavigationActions(rememberNavController())
        )
    }
}