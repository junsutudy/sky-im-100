package app.junsu.imback.features.main

import android.annotation.SuppressLint
import androidx.compose.animation.core.EaseOutCirc
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.paging.compose.LazyPagingItems
import app.junsu.imback.data.photo.model.Photo
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
    photoPagingItems: LazyPagingItems<Photo>,
    onOpenPhotoDetails: (currentPosition: Int) -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    var selectedTab by remember { mutableStateOf(MainDestinations.PHOTO_LIST) }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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
            modifier = Modifier.padding(PaddingValues(bottom = paddingValues.calculateBottomPadding())),
            enterTransition = {
                fadeIn(
                    animationSpec = tween(durationMillis = 250),
                ) + scaleIn(
                    animationSpec = tween(
                        durationMillis = 250,
                        easing = EaseOutCirc,
                    ),
                    initialScale = 0.99f,
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(durationMillis = 250),
                ) + scaleOut(
                    animationSpec = tween(
                        durationMillis = 250,
                        easing = EaseOutCirc,
                    ),
                    targetScale = 0.99f,
                )
            },
        ) {
            composable(MainDestinations.PHOTO_LIST.route) {
                PhotoListTab(
                    photoPagingItems = photoPagingItems,
                    onOpenPhotoDetails = onOpenPhotoDetails,
                )
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