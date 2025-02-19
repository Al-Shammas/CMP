package org.cmpbachelor.project.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.cmpbachelor.project.catalog.presentation.product_list.ProductListScreenRoot
import org.cmpbachelor.project.home.presentation.Greeting
import org.cmpbachelor.project.home.presentation.HomeScreen
import org.cmpbachelor.project.nfc.presentation.NFCScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController, startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(Screen.Greeting.route) {
            Greeting()
        }
        composable(Screen.NFC.route) {
            NFCScreen()
        }
        composable(Screen.ShoppingCart.route) {
            ProductListScreenRoot(onProductClick = {})
        }
    }
}
