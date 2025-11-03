package app.junsu.imback

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import app.junsu.imback.core.ui.theme.IMBACKTheme
import app.junsu.imback.features.main.MainScreen
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
fun ImBackApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ImBackDestinations.MAIN.route,
        modifier = modifier,
    ) {
        composable(ImBackDestinations.MAIN.route) {
            MainScreen(
                onOpenPhotoDetails = { photoId ->
                    navController.navigate(route = ImBackDestinations.PHOTO_DETAILS.route + "/$photoId")
                }
            )
        }
        composable(
            route = ImBackDestinations.PHOTO_DETAILS.route + "/{photoId}",
            arguments = listOf(
                navArgument("photoId") {
                    type = NavType.LongType
                    nullable = false
                },
            )
        ) {
            val photoId = it.arguments?.getLong("photoId")!!
            PhotoDetailsScreen(
                photoId = photoId,
                onNavigateUp = navController::navigateUp,
            )
        }
    }
}
