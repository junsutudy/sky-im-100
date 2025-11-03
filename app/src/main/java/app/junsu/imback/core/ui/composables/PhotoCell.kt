package app.junsu.imback.core.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import app.junsu.imback.BuildConfig
import app.junsu.imback.data.photo.model.Photo
import coil.compose.AsyncImage

@Composable
fun PhotoCell(
    photo: Photo,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    AsyncImage(
        model = "${BuildConfig.BASE_URL}/id/${photo.id}/128/128",
        contentDescription = null,
        modifier = modifier
            .clickable(
                onClick = onClick,
            )
            .fillMaxSize(),
        contentScale = ContentScale.Crop,
    )
}