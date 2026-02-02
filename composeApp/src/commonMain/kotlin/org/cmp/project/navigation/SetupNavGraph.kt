package org.cmp.project.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
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
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.navigation.toRoute
import org.cmp.project.catalog.presentation.SelectedProductViewModel
import org.cmp.project.catalog.presentation.cart.CartScreenRoot
import org.cmp.project.catalog.presentation.cart.CartViewModel
import org.cmp.project.catalog.presentation.product_details.ProductDetailAction
import org.cmp.project.catalog.presentation.product_details.ProductDetailScreenRoot
import org.cmp.project.catalog.presentation.product_details.ProductDetailViewModel
import org.cmp.project.catalog.presentation.product_list.ProductListScreenRoot
import org.cmp.project.catalog.presentation.product_list.ProductListViewModel
import org.cmp.project.core.presentation.componants.BottomBar
import org.cmp.project.scan.presentation.ScanAction
import org.koin.compose.viewmodel.koinViewModel
import org.cmp.project.scan.presentation.ScanScreenRoot
import org.cmp.project.scan.presentation.ScanViewModel

@Composable
fun SetupNavGraph() {

    val navController = rememberNavController()

    Scaffold(bottomBar = {
        BottomBar(navController)
    }) {

        NavHost(
            navController = navController, startDestination = Route.CatalogGraph,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 74.dp)
        ) {
            navigation<Route.CatalogGraph>(
                startDestination = Route.Catalog
            ) {
                composable<Route.Catalog> {
                    val viewModel = koinViewModel<ProductListViewModel>()
                    val selectedProductViewModel =
                        it.sharedKoinViewModel<SelectedProductViewModel>(navController)
                    LaunchedEffect(true){
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
                        if (selectedProduct != null) {
                            viewModel.onAction(
                                ProductDetailAction.OnSelectedProductChange(selectedProduct!!)
                            )
                        } else {
                            viewModel.onAction(ProductDetailAction.FetchProductById)
                        }
                    }

                    ProductDetailScreenRoot(
                        viewModel = viewModel,
                        onBackClick = { navController.navigateUp() }
                    )

                }

                composable<Route.Cart> {
                    val viewModel = koinViewModel<CartViewModel>()
                    CartScreenRoot(
                        viewModel = viewModel,
                        onCheckout = {}
                    )
                }

                composable<Route.Scan> {
                    val viewModel = koinViewModel<ScanViewModel>()
                    val state by viewModel.state.collectAsStateWithLifecycle()

                    LaunchedEffect(state.productId) {
                        state.productId?.let { productId ->
                            navController.navigate(Route.ProductDetail(productId)) {
                                popUpTo(Route.Scan) {
                                    inclusive = false
                                }
                            }
                            viewModel.onAction(ScanAction.OnNavigationHandled)
                        }
                    }

                    ScanScreenRoot(
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