package app.junsu.imback.features.photo_details

import PhotoViewer
import android.annotation.SuppressLint
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import app.junsu.imback.data.photo.model.Photo
import coil.compose.AsyncImage
import coil.imageLoader
import coil.request.ImageRequest
import kotlinx.coroutines.Dispatchers
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoDetailsScreen(
    photoPagingItems: LazyPagingItems<Photo>,
    currentIndex: Int,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PhotoDetailsViewModel = hiltViewModel(),
) {
    val state by viewModel.collectAsState()
    val pagerState = rememberPagerState(initialPage = currentIndex) { photoPagingItems.itemCount }

    viewModel.collectSideEffect {
        when (it) {
            PhotoDetailsSideEffect.Loading -> println("PHOTO DETAILS LOADING ENTERED")
        }
    }

    LaunchedEffect(key1 = pagerState.currentPage) {
        val currentPhoto = photoPagingItems[pagerState.currentPage]
        if (currentPhoto != null) {
            if (state.photoDetails?.id != currentPhoto.id) {
                viewModel.loadPhotoDetails(photoId = currentPhoto.id)
            }
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter") Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Photo Details") },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateUp,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            println("BOOKMARK")
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Bookmark,
                            contentDescription = null,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                ),
            )
        },
    ) { paddingValues ->

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            // FIXME: NOT USING DETAILS VIEWMODEL DATA
            HorizontalPager(
                state = pagerState,
            ) { page ->
                val photo = photoPagingItems[page]
                val photoDetails = state.photoDetails

                if (photo != null) {
                    val highResUrl = if (photoDetails?.id == photo.id) {
                        photoDetails.downloadUrl
                    } else {
                        null
                    }

                    var isHighResLoaded by remember { mutableStateOf(false) }
                    val context = LocalContext.current

                    LaunchedEffect(key1 = highResUrl) {
                        if (highResUrl == null) {
                            isHighResLoaded = false
                            return@LaunchedEffect
                        }

                        isHighResLoaded = false
                        val request = ImageRequest.Builder(context)
                            .data(highResUrl)
                            .dispatcher(Dispatchers.IO)
                            .build()
                        try {
                            context.imageLoader.execute(request)
                            isHighResLoaded = true
                        } catch (_: Exception) {
                            isHighResLoaded = false
                        }
                    }

                    PhotoViewer {
                        Crossfade(
                            targetState = isHighResLoaded,
                            label = "photo_cross_fade",
                            animationSpec = tween(300),
                        ) { isLoaded ->
                            if (isLoaded) {
                                AsyncImage(
                                    model = highResUrl,
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxWidth(),
                                    contentScale = ContentScale.FillWidth,
                                    alignment = Alignment.Center,
                                )
                            } else {
                                AsyncImage(
                                    model = photo.downloadUrl,
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxWidth(),
                                    contentScale = ContentScale.FillWidth,
                                    alignment = Alignment.Center,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
