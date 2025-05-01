package org.cmpbachelor.project.catalog.presentation.product_details

import org.cmpbachelor.project.catalog.domain.Product

data class  ProductDetailsState(
    val isLoading: Boolean = false,
    val isFavorite: Boolean = false,
    val product: Product? = null,
)
