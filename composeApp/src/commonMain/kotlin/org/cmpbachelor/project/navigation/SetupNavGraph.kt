package navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ui.presentation.home.Greeting
import ui.presentation.home.HomeScreen

@Composable
fun SetupNavGraph() {

    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController, startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(Screen.Greeting.route) {
            Greeting()
        }
        composable(Screen.NFC.route) {

        }
    }
}
