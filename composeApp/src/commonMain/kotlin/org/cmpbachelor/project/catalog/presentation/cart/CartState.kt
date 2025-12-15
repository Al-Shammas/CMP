package org.cmpbachelor.project.catalog.presentation.cart

import org.cmpbachelor.project.catalog.data.database.CartItemEntity

data class CartState(
    val isLoading: Boolean = true,
    val items: List<CartItemEntity> = emptyList(),
    val totalPrice: Double = 0.0
)