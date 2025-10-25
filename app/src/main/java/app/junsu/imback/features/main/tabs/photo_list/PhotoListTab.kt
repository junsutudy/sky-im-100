package app.junsu.imback.features.main.tabs.photo_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun PhotoListTab(
    modifier: Modifier = Modifier,
    viewModel: PhotoListViewModel = hiltViewModel(),
) {
    Scaffold(
        modifier = modifier,
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            Text("PHOTO LIST SCREEN")
        }
    }
}