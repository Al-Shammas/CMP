package org.cmp.project.navigation

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object CatalogGraph: Route

    @Serializable
    data object Catalog : Route

    @Serializable
    data class ProductDetail(val id: Int) : Route

    @Serializable
    data object Cart : Route

    @Serializable
    data object Scan : Route
}