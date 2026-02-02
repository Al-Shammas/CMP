package org.cmp.project.core.presentation.componants

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import cmp.composeapp.generated.resources.Res
import cmp.composeapp.generated.resources.baseline_home_24
import cmp.composeapp.generated.resources.baseline_qr_code_scanner_24
import cmp.composeapp.generated.resources.cart
import cmp.composeapp.generated.resources.home
import cmp.composeapp.generated.resources.scan
import org.cmp.project.navigation.Route

@OptIn(ExperimentalResourceApi::class)
@Composable
fun BottomBar(navController: NavController) {
    var selectedRoute: Route by remember { mutableStateOf(Route.CatalogGraph) }

    NavigationBar(
        containerColor = Color.White,
        contentColor = Color.Black
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(resource = Res.drawable.baseline_home_24),
                    contentDescription = null
                )
            },
            label = { Text(text = stringResource(resource = Res.string.home)) },
            selected = selectedRoute == Route.CatalogGraph,
            onClick = {
                selectedRoute = Route.CatalogGraph
                navController.navigate(route = Route.CatalogGraph)
            }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = null,
                    tint = Color.Black
                )
            },
            label = { Text(text = stringResource(resource = Res.string.cart)) },
            selected = selectedRoute == Route.Cart,
            onClick = {
                selectedRoute = Route.Cart
                navController.navigate(route = Route.Cart)
            }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(resource = Res.drawable.baseline_qr_code_scanner_24),
                    contentDescription = null,
                )
            },
            label = { Text(text = stringResource(resource = Res.string.scan)) },
            selected = selectedRoute == Route.Scan,
            onClick = {
                selectedRoute = Route.Scan
                navController.navigate(route = Route.Scan)
            }
        )
    }
}