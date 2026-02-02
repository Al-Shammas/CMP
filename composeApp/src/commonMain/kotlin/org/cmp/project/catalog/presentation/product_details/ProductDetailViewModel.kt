package org.cmp.project.catalog.presentation.product_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.cmp.project.catalog.data.database.CartItemEntity
import org.cmp.project.catalog.domain.ProductRepository
import org.cmp.project.catalog.domain.ShoppingCartRepository
import org.cmp.project.core.domain.onError
import org.cmp.project.core.domain.onSuccess
import org.cmp.project.navigation.Route

class ProductDetailViewModel(
    private val productRepository: ProductRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val productId = savedStateHandle.toRoute<Route.ProductDetail>().id

    private val _uiEvents = Channel<UiEvent>(Channel.BUFFERED)
    val uiEvents = _uiEvents.receiveAsFlow()


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

    private fun fetchProductById(id: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            productRepository.getProducts()
                .onSuccess { products ->
                    val product = products.find { it.id == id }
                    _state.update {
                        it.copy(
                            product = product,
                            isLoading = false
                        )
                    }
                }
                .onError { error ->
                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                }
        }
    }

    fun onAction(action: ProductDetailAction) {
        when (action) {
            is ProductDetailAction.AddToShoppingCart ->  {
                viewModelScope.launch {
                    state.value.product?.let { product ->
                        val entity = CartItemEntity(
                            productId = product.id,
                            title = product.title,
                            price = product.price,
                            thumbnail = product.thumbnail
                        )
                        shoppingCartRepository.addProductToCart(entity)
                        _uiEvents.send(UiEvent.ShowSnackBar("Added \"${product.title}\" to cart!"))
                    }
                }
            }

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

            is ProductDetailAction.FetchProductById -> {
                // Explicit fetch request from navigation (for Scan case)
                fetchProductById(productId)
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

sealed class UiEvent {
    data class ShowSnackBar(val message: String) : UiEvent()
}