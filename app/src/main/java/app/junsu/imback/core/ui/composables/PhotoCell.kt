package app.junsu.imback.core.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.junsu.imback.BuildConfig
import app.junsu.imback.data.photo.model.Photo
import coil.compose.AsyncImage

@Composable
fun PhotoCell(
    photo: Photo,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .clickable(
                onClick = onClick,
            ),
    ) {
        println("${BuildConfig.BASE_URL}/100/100")
        AsyncImage(
            model = "${BuildConfig.BASE_URL}/100/100",
            contentDescription = null,
        )
    }
}