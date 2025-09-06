package com.logmind.tasklog.core.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.logmind.tasklog.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TklTopAppBar(openDrawer: () -> Unit, navigateToProfile: () -> Unit) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Unspecified
        ),
        title = { Text(stringResource(R.string.app_name)) },
        navigationIcon = {
            IconButton(onClick = openDrawer) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "menu")
            }
        },
        actions = {
            TklProfileAvatar(
                onClick = navigateToProfile,
                size = dimensionResource(R.dimen.avatar_xs),
                iconSize = dimensionResource(R.dimen.icon_size_md)
            )
            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.spacing_sm)))
        },
    )

}
