package app.junsu.imback

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import app.junsu.imback.core.ui.theme.IMBACKTheme
import app.junsu.imback.features.main.MainScreen
import app.junsu.imback.features.main.MainViewModel
import app.junsu.imback.features.photo_details.PhotoDetailsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IMBACKTheme {
                ImBackApp()
            }
        }
    }
}

enum class ImBackDestinations(
    val route: String,
) {
    MAIN(
        route = "/main",
    ),
    PHOTO_DETAILS(
        route = "/photo_details",
    ),
}

@Composable
fun ImBackApp(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel(),
) {
    val navController = rememberNavController()
    val photoPagingItems = viewModel.pagingDataFlow.collectAsLazyPagingItems()
    NavHost(
        navController = navController,
        startDestination = ImBackDestinations.MAIN.route,
        modifier = modifier,
        enterTransition = {
            fadeIn(
                animationSpec = tween(
                    easing = FastOutSlowInEasing,
                    durationMillis = 250,
                ),
            )
        },
        exitTransition = {
            fadeOut(
                animationSpec = tween(
                    easing = FastOutSlowInEasing,
                    durationMillis = 250,
                ),
            )
        }
    ) {
        composable(ImBackDestinations.MAIN.route) {
            MainScreen(
                photoPagingItems = photoPagingItems,
                onOpenPhotoDetails = { currentPosition ->
                    navController.navigate(route = ImBackDestinations.PHOTO_DETAILS.route + "/$currentPosition")
                }
            )
        }
        composable(
            route = ImBackDestinations.PHOTO_DETAILS.route + "/{currentIndex}",
            arguments = listOf(
                navArgument("currentIndex") {
                    type = NavType.IntType
                    nullable = false
                },
            )
        ) {
            val currentIndex = it.arguments?.getInt("currentIndex")!!
            PhotoDetailsScreen(
                photoPagingItems = photoPagingItems,
                currentIndex = currentIndex,
                onNavigateUp = navController::navigateUp,
            )
        }
    }
}
