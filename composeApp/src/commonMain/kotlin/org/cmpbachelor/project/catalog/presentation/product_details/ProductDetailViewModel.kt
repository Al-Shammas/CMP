package org.cmpbachelor.project.catalog.presentation.product_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.cmpbachelor.project.catalog.domain.ProductRepository
import org.cmpbachelor.project.navigation.Route

class ProductDetailViewModel(
    private val productRepository: ProductRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val productId = savedStateHandle.toRoute<Route.ProductDetail>().id

    private val _state = MutableStateFlow(ProductDetailsState())
    val state = _state
        .onStart {
            observeFavoriteStatus()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onAction(action: ProductDetailAction) {
        when (action) {
            is ProductDetailAction.AddToShoppingCart -> TODO()

            is ProductDetailAction.OnFavoriteClick -> {
                viewModelScope.launch {
                    if (state.value.isFavorite) {
                        productRepository.deleteFavorite(productId)
                    } else {
                        state.value.product?.let { product ->
                            productRepository.markAsFavorite(product)
                        }
                    }

                }
            }

            is ProductDetailAction.OnSelectedProductChange -> _state.update {
                it.copy(
                    product = action.product
                )
            }

            else -> Unit
        }

    }

    private fun observeFavoriteStatus() {
        productRepository
            .isProductFavorite(productId)
            .onEach { isFavorite ->
                _state.update { it.copy(
                    isFavorite = isFavorite
                ) }
            }
            .launchIn(viewModelScope)
    }
}