package com.logmind.tasklog.presentation.screens.home.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.logmind.tasklog.R
import com.logmind.tasklog.presentation.components.ProfileAvatar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenTopAppBar(openDrawer: () -> Unit, navigateToProfile: () -> Unit) {
    CenterAlignedTopAppBar(
        title = { Text(stringResource(R.string.app_name)) },
        navigationIcon = {
            IconButton(onClick = openDrawer) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "menu")
            }
        },
        actions = {
            ProfileAvatar(
                onClick = navigateToProfile,
                imageUri = stringResource(R.string.mock_profile_image_url),
                size = dimensionResource(R.dimen.avatar_sm)
            )
            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.spacing_sm)))

        }
    )
}
