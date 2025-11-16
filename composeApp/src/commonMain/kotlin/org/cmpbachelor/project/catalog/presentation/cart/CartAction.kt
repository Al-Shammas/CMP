package org.cmpbachelor.project.catalog.presentation.cart

sealed interface CartAction {
    data object LoadCart : CartAction
    data class IncreaseQuantity(val productId: Int) : CartAction
    data class DecreaseQuantity(val productId: Int) : CartAction
    data class RemoveItem(val productId: Int) : CartAction
    data object Checkout : CartAction
}