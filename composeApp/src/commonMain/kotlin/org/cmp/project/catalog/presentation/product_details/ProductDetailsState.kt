package org.cmp.project.catalog.presentation.product_details

import org.cmp.project.catalog.domain.Product

data class  ProductDetailsState(
    val isLoading: Boolean = false,
    val isFavorite: Boolean = false,
    val product: Product? = null,
)
