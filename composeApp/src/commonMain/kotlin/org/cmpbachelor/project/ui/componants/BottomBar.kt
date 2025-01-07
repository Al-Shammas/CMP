package org.cmpbachelor.project.ui.componants

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cmpbachelor.composeapp.generated.resources.Res
import cmpbachelor.composeapp.generated.resources.bag_icon
import cmpbachelor.composeapp.generated.resources.baseline_home_24
import cmpbachelor.composeapp.generated.resources.home
import cmpbachelor.composeapp.generated.resources.second
import cmpbachelor.composeapp.generated.resources.star_icon
import cmpbachelor.composeapp.generated.resources.third
import navigation.Screen
import org.cmpbachelor.project.ui.componants.BottomBarDims.iconSize
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun BottomBar(navController: NavController) {
    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color.Black
    ) {
        BottomNavigationItem(
            icon = {
                Icon(
                    painter = painterResource(resource = Res.drawable.baseline_home_24),
                    ""
                )
            },
            label = { Text(text = stringResource(resource = Res.string.home)) },
            selected = true,
            onClick = { navController.navigate(route = Screen.Home.route)}
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    painter = painterResource(resource = Res.drawable.bag_icon),
                    contentDescription = "",
                    modifier = Modifier.size(iconSize).padding(3.dp)
                )
            },
            label = { Text(text = stringResource(resource = Res.string.second)) },
            selected = false,
            onClick = { /*Handle click here*/ }
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    painter = painterResource(resource = Res.drawable.star_icon),
                    contentDescription = "",
                    modifier = Modifier.size(iconSize)
                )
            },
            label = { Text(text = stringResource(Res.string.third)) },
            selected = false,
            onClick = { /*Handle click here*/ }
        )
    }
}

private object BottomBarDims {
    val iconSize = 24.dp
}