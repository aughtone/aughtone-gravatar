package io.github.aughtone.gravatar.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import io.github.aughtone.gravatar.Avatar
import io.github.aughtone.gravatar.Gravatar
import io.github.aughtone.gravatar.gravatarUrlOf

@Composable
fun GravatarImage(
    email: String,
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    contentDescription: String? = "Gravatar",
    defaultImage: Gravatar.DefaultImage? = null,
    rating: Avatar.Rating = Avatar.Rating.g,
    forceDefault: Boolean = false,
    contentScale: ContentScale = ContentScale.Crop,
    circle: Boolean = true,
) {
    val url = gravatarUrlOf(
        email = email,
        sizeInPixels = (size.value * 2).toInt(), // Load 2x size for better quality
        defaultImage = defaultImage,
        rating = rating,
        forceDefault = forceDefault
    )

    Box(
        modifier = modifier
            .size(size)
            .then(if (circle) Modifier.clip(CircleShape) else Modifier)
    ) {
        AsyncImage(
            model = url,
            contentDescription = contentDescription,
            modifier = Modifier.matchParentSize(),
            contentScale = contentScale
        )
    }
}
