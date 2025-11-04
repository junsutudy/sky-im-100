package app.junsu.imback.features.photo_details

import PhotoViewer
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import app.junsu.imback.data.photo.model.Photo
import coil.compose.AsyncImage
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
        modifier = modifier, topBar = {
            TopAppBar(title = { Text("Photo Details") }, navigationIcon = {
                IconButton(
                    onClick = onNavigateUp,
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                    )
                }
            }, actions = {
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
            })
        }) { paddingValues ->

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
                    val imageUrl =
                        if (photoDetails?.id == photo.id) {
                            photoDetails.downloadUrl
                        } else {
                            null
                        }

                    PhotoViewer {
                        if (imageUrl != null) {
                            AsyncImage(
                                model = imageUrl,
                                contentDescription = null,
                                contentScale = ContentScale.FillWidth,
                                alignment = Alignment.Center,
                            )
                        } else {
                            AsyncImage(
                                model = photo.downloadUrl,
                                contentDescription = null,
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