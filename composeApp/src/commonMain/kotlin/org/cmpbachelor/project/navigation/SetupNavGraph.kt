package org.cmpbachelor.project.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import org.cmpbachelor.project.catalog.presentation.SelectedProductViewModel
import org.cmpbachelor.project.catalog.presentation.product_details.ProductDetailAction
import org.cmpbachelor.project.catalog.presentation.product_details.ProductDetailScreenRoot
import org.cmpbachelor.project.catalog.presentation.product_details.ProductDetailViewModel
import org.cmpbachelor.project.catalog.presentation.product_list.ProductListScreenRoot
import org.cmpbachelor.project.catalog.presentation.product_list.ProductListViewModel
import org.cmpbachelor.project.core.presentation.componants.BottomBar
import org.cmpbachelor.project.home.presentation.Greeting
import org.cmpbachelor.project.home.presentation.HomeScreen
import org.cmpbachelor.project.home.presentation.HomeViewModel
import org.cmpbachelor.project.nfc.presentation.NFCScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SetupNavGraph() {

    val navController = rememberNavController()

    Scaffold(bottomBar = {
        BottomBar(navController)
    }) {

    NavHost(
        navController = navController, startDestination = Route.Home,
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 74.dp)
    ) {
        composable<Route.Home> {
            val viewModel = koinViewModel<HomeViewModel>()
            HomeScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable<Route.Greeting> {
            Greeting()
        }

        composable<Route.NFC> {
            NFCScreen()
        }

        navigation<Route.CatalogGraph>(
            startDestination = Route.Catalog
        ) {
            composable<Route.Catalog> {
                val viewModel = koinViewModel<ProductListViewModel>()
                val selectedProductViewModel =
                    it.sharedKoinViewModel<SelectedProductViewModel>(navController)
                LaunchedEffect(true) {
                    selectedProductViewModel.onSelectedProduct(null)
                }
                ProductListScreenRoot(
                    viewModel = viewModel,
                    onProductClick = { product ->
                        selectedProductViewModel.onSelectedProduct(product = product)
                        navController.navigate(Route.ProductDetail(product.id))
                    })
            }
            composable<Route.ProductDetail> { entry ->
                val args = entry.toRoute<Route.ProductDetail>()
                val selectedProductViewModel =
                    entry.sharedKoinViewModel<SelectedProductViewModel>(navController)
                val viewModel = koinViewModel<ProductDetailViewModel>()
                val selectedProduct by selectedProductViewModel.selectedProduct.collectAsStateWithLifecycle()

                LaunchedEffect(selectedProduct) {
                    selectedProduct?.let {
                        viewModel.onAction(
                            ProductDetailAction.OnSelectedProductChange(
                                it
                            )
                        )
                    }
                }

                ProductDetailScreenRoot(
                    viewModel = viewModel,
                    onBackClick = { navController.navigateUp() }
                )
            }
        }
    }
    }
}

@Composable
private inline fun <reified T : ViewModel> NavBackStackEntry.sharedKoinViewModel(
    navController: NavController,
): T {
    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return koinViewModel(
        viewModelStoreOwner = parentEntry
    )
}