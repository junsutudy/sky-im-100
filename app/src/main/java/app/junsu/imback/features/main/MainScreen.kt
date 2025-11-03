package app.junsu.imback.features.main

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.junsu.imback.features.main.tabs.library.LibraryTab
import app.junsu.imback.features.main.tabs.photo_list.PhotoListTab

enum class MainDestinations(
    val route: String,
    val icon: ImageVector,
) {
    PHOTO_LIST(
        route = "/photo_list",
        icon = Icons.AutoMirrored.Filled.List,
    ),
    LIBRARY(
        route = "/library",
        icon = Icons.Default.Email
    ),
}

@Composable
fun MainScreen(
    onOpenPhotoDetails: (photoId: Long) -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    var selectedTab by remember { mutableStateOf(MainDestinations.PHOTO_LIST) }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            BottomAppBar {
                MainDestinations.entries.forEach { destination ->
                    NavigationBarItem(
                        selected = destination == selectedTab,
                        onClick = {
                            navController.navigateToDestination(destination)
                            selectedTab = destination
                        },
                        icon = {
                            Icon(
                                imageVector = destination.icon,
                                contentDescription = null,
                            )
                        },
                        label = {
                            Text("Photos")
                        },
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = MainDestinations.PHOTO_LIST.route,
            modifier = Modifier
                .padding(paddingValues)
                .consumeWindowInsets(paddingValues),
        ) {
            composable(MainDestinations.PHOTO_LIST.route) {
                PhotoListTab()
            }
            composable(MainDestinations.LIBRARY.route) {
                LibraryTab()
            }
        }
    }
}

private fun NavHostController.navigateToDestination(destination: MainDestinations) =
    this.navigate(destination.route) {
        launchSingleTop = true
        restoreState = true
    }