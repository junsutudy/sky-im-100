package app.junsu.imback.features.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.junsu.imback.features.main.tabs.library.LibraryTab
import app.junsu.imback.features.main.tabs.photo_list.PhotoListTab

enum class MainDestinations(
    val route: String,
) {
    PHOTO_LIST(
        route = "/photo_list",
    ),
    LIBRARY(
        route = "/library",
    ),
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = MainDestinations.PHOTO_LIST.route,
        modifier = modifier,
    ) {
        composable(MainDestinations.PHOTO_LIST.route) {
            PhotoListTab()
        }
        composable(MainDestinations.LIBRARY.route) {
            LibraryTab()
        }
    }
}