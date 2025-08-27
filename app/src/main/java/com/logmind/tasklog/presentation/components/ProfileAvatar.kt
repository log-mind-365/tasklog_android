package com.logmind.tasklog.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.logmind.tasklog.R

@Composable
fun ProfileAvatar(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    imageUri: String,
    size: Dp = dimensionResource(R.dimen.avatar_md),
) {
    Box(
        modifier = modifier.clickable(
            onClick = onClick,
            indication = ripple(
                bounded = false,
                radius = size * 0.7f
            ),
            interactionSource = remember { MutableInteractionSource() }
        )
    ) {
        AsyncImage(
            model = imageUri,
            contentDescription = "avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
        )
        Box(
            modifier = Modifier
                .size(size * 0.3f)
                .background(color = MaterialTheme.colorScheme.primary, shape = CircleShape)
                .border(2.dp, MaterialTheme.colorScheme.onPrimary, CircleShape)
                .align(Alignment.BottomEnd)
        )
    }
}
