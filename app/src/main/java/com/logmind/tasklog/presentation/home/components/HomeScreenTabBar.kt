package com.logmind.tasklog.presentation.home.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.logmind.tasklog.presentation.home.tabMenus


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenTabBar(selectedFolder: Int, onClick: (Int) -> Unit) {
    SecondaryTabRow(
        selectedTabIndex = selectedFolder,
    ) {
        tabMenus.forEachIndexed { index, title ->
            Tab(
                selected = index == selectedFolder,
                onClick = { onClick(index) },
                text = { Text(text = title) }
            )
        }
    }
}