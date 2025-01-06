package org.cmpbachelor.project.domain

import navigation.Screen

data class HomeScreenListItem(
    val tile: String,
    val route: String,
)

val HomeScreenItemsList = listOf<HomeScreenListItem>(
    HomeScreenListItem("Greeting", Screen.Greeting.route),
    HomeScreenListItem("NFC", Screen.Greeting.route),
    HomeScreenListItem("Third", Screen.Greeting.route),
    HomeScreenListItem("Forth", Screen.Greeting.route),
)
