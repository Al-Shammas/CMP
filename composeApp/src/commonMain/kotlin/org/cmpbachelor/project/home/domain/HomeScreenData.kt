package org.cmpbachelor.project.domain

import org.cmpbachelor.project.navigation.Screen

data class HomeScreenListItem(
    val tile: String,
    val route: String,
)

val HomeScreenItemsList = listOf<HomeScreenListItem>(
    HomeScreenListItem("Greeting", Screen.Greeting.route),
    HomeScreenListItem("NFC", Screen.NFC.route),
    HomeScreenListItem("ShoppingCart", Screen.ShoppingCart.route),
    HomeScreenListItem("Forth", Screen.Greeting.route),
)
