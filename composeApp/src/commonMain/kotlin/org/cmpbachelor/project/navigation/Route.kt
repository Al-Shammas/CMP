package org.cmpbachelor.project.navigation

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object Home: Route

    @Serializable
    data object NFC: Route

    @Serializable
    data object Greeting: Route

    @Serializable
    data object CatalogGraph: Route

    @Serializable
    data object Catalog : Route

    @Serializable
    data class ProductDetail(val id: Int) : Route
}