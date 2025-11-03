import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import app.junsu.imback.data.photo.model.PhotoDetails
import coil.compose.AsyncImage

@Composable
fun PhotoViewer(
    modifier: Modifier = Modifier,
    photoDetails: PhotoDetails?,
) {
    var scale by remember { mutableFloatStateOf(1f) }

    photoDetails?.let { details ->
        Box(
            modifier = modifier.pointerInput(Unit) {
                detectTransformGestures { _, _, zoom, _ ->
                    if (scale <= 1) {
                        if (zoom < 1) {
                            scale = 1f
                            return@detectTransformGestures
                        }
                    }
                    if (scale >= 2) {
                        if (zoom > 1) {
                            scale = 2f
                            return@detectTransformGestures
                        }
                    }

                    scale *= zoom
                }
            },
            contentAlignment = Alignment.Center,
        ) {
            AsyncImage(
                modifier = Modifier.graphicsLayer(
                    scaleX = maxOf(.5f, minOf(3f, scale)),
                    scaleY = maxOf(.5f, minOf(3f, scale)),
                ),
                model = details.downloadUrl,
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                alignment = Alignment.Center,
            )
        }
    }
}