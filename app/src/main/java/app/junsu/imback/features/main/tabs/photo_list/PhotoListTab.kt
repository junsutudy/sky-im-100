package app.junsu.imback.features.main.tabs.photo_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.junsu.imback.core.ui.composables.PhotoCell

@Composable
fun PhotoListTab(
    modifier: Modifier = Modifier,
    viewModel: PhotoListViewModel = hiltViewModel(),
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        modifier = modifier,
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets.exclude(NavigationBarDefaults.windowInsets)
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(count = 3),
            modifier = Modifier.padding(paddingValues),
        ) {
            state.value.photos.let { photos ->
                if (photos.isNotEmpty()) {
                    items(photos) { photo ->
                        PhotoCell(
                            photo = photo,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.0.dp)
                                .background(color = Color.Blue),
                        ) {

                        }
                    }
                }
            }
        }
    }
}