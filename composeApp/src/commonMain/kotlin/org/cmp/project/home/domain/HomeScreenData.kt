package org.cmp.project.home.domain

import org.cmp.project.navigation.Route

data class HomeScreenListItem(
    val tile: String,
    val route: Route,
)

val HomeScreenItemsList = listOf<HomeScreenListItem>(
//    HomeScreenListItem("Greeting", Route.Greeting),
//    HomeScreenListItem("NFC", Route.NFC),
//    HomeScreenListItem("catalog", Route.Catalog),
)
