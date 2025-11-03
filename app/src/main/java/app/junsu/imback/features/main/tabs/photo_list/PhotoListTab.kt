package app.junsu.imback.features.main.tabs.photo_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.junsu.imback.core.ui.composables.PhotoCell

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoListTab(
    onOpenPhotoDetails: (photoId: Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PhotoListViewModel = hiltViewModel(),
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    val textFieldState = rememberTextFieldState()
    var expanded by rememberSaveable { mutableStateOf(false) }
    val searchResults = remember { mutableListOf<String>("ASDF", "ADSFSDF") }

    Scaffold(
        modifier = modifier,
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics { isTraversalGroup = true }
            ) {
                SearchBar(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .semantics { traversalIndex = 0f },
                    inputField = {
                        SearchBarDefaults.InputField(
                            query = textFieldState.text.toString(),
                            onQueryChange = { textFieldState.edit { replace(0, length, it) } },
                            onSearch = {
                                // onSearch(textFieldState.text.toString())
                                expanded = false
                            },
                            expanded = expanded,
                            onExpandedChange = { expanded = it },
                            placeholder = { Text("Search") },
                            trailingIcon = {
                                IconButton(onClick = {}) {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = null,
                                    )
                                }
                            },
                        )
                    },
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                ) {
                    // Display search results in a scrollable column
                    Column(Modifier.verticalScroll(rememberScrollState())) {
                        searchResults.forEach { result ->
                            ListItem(
                                headlineContent = { Text(result) },
                                modifier = Modifier
                                    .clickable {
                                        textFieldState.edit { replace(0, length, result) }
                                        expanded = false
                                    }
                                    .fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(count = 4),
            contentPadding = PaddingValues(
                top = paddingValues.calculateTopPadding() + 8.0.dp,
                bottom = paddingValues.calculateBottomPadding(),
            ),
            horizontalArrangement = Arrangement.spacedBy(space = 2.0.dp),
            verticalArrangement = Arrangement.spacedBy(space = 2.0.dp),
        ) {
            state.value.photos.let { photos ->
                if (photos.isNotEmpty()) {
                    items(photos, key = { photo -> photo.id }) { photo ->
                        PhotoCell(
                            photo = photo,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1.0f),
                            onClick = { onOpenPhotoDetails.invoke(photo.id) },
                        )
                    }
                }
            }
        }
    }
}
