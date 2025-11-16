package org.cmpbachelor.project.catalog.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.cmpbachelor.project.catalog.domain.ShoppingCartRepository

class CartViewModel(
    private val shoppingCartRepository: ShoppingCartRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(CartState())
    val state = _state
        // observe the DAO flow, recalculate total
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            _state.value
        )

    init {
        viewModelScope.launch {
            shoppingCartRepository
                .getCartItems()
                .map { items ->
                    val total = items.sumOf { it.price * it.quantity }
                    CartState(isLoading = false, items = items, totalPrice = total)
                }
                .collect { _state.value = it }
        }
    }

    fun onAction(action: CartAction) {
        viewModelScope.launch {
            when (action) {
                is CartAction.IncreaseQuantity -> {
                    val item = _state.value.items.firstOrNull { it.productId == action.productId }
                    if (item != null) {
                        shoppingCartRepository.addProductToCart(item)
                    }
                }

                is CartAction.DecreaseQuantity -> {
                    shoppingCartRepository.removeOneFromCart(action.productId)
                }

                is CartAction.RemoveItem -> {
                    shoppingCartRepository.deleteProductFromCart(action.productId)
                }

                CartAction.Checkout -> {
                }

                CartAction.LoadCart -> Unit
            }
        }
    }
}