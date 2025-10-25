package app.junsu.imback.features.main.tabs.library

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LibraryTab(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier,
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            Text("AND WELCOME TO LIBRARY TAB")
        }
    }
}