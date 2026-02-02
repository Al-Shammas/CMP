package org.cmp.project.catalog.presentation.cart

import org.cmp.project.catalog.data.database.CartItemEntity

data class CartState(
    val isLoading: Boolean = true,
    val items: List<CartItemEntity> = emptyList(),
    val totalPrice: Double = 0.0
)