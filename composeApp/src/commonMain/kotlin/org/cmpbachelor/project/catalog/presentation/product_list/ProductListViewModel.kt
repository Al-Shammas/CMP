@file:OptIn(FlowPreview::class)

package org.cmpbachelor.project.catalog.presentation.product_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.cmpbachelor.project.catalog.domain.Product
import org.cmpbachelor.project.catalog.domain.ProductRepository
import org.cmpbachelor.project.core.domain.onError
import org.cmpbachelor.project.core.domain.onSuccess
import org.cmpbachelor.project.core.presentation.toUiText

class ProductListViewModel(
    private val productRepository: ProductRepository,
) : ViewModel() {

    private var cachedProducts = emptyList<Product>()
    private var searchJob: Job? = null
    private var observeFavoriteJob: Job? = null

    private val _state = MutableStateFlow(ProductListState())
    val state = _state
        .onStart {
            observeSearchQuery()
            if(cachedProducts.isEmpty()) {
                fetchProducts()
            }
            observeFavoriteProduct()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onAction(action: ProductListAction) {
        when (action) {
            is ProductListAction.OnProductClick -> {

            }

            is ProductListAction.OnSearchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = action.query)
                }

            }

            is ProductListAction.OnTabSelected -> {
                _state.update {
                    it.copy(selectedTabIndex = action.index)
                }
            }
        }
    }

    private fun fetchProducts() = viewModelScope.launch {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
        productRepository
            .getProducts()
            .onSuccess { productsResults ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = null,
                        productListResults = productsResults
                    )
                }
                cachedProducts = productsResults
            }
            .onError { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        productListResults = emptyList(),
                        errorMessage = error.toUiText()
                    )
                }
            }
    }


    private fun observeFavoriteProduct() {
        observeFavoriteJob?.cancel()
        observeFavoriteJob = productRepository
            .getFavoriteProducts()
            .onEach { favoriteProduct ->
                _state.update { it.copy(
                    favoriteProducts = favoriteProduct
                ) }
            }
            .launchIn(viewModelScope)
    }


    private fun observeSearchQuery() {
        state
            .map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { query ->
                when {
                    query.isBlank() -> {
                        _state.update {
                            it.copy(
                                errorMessage = null,
                                searchResults = emptyList(),
                                productListResults = cachedProducts
                            )
                        }
                    }

                    query.length >= 2 -> {
                        searchJob?.cancel()
                        searchJob = searchProducts(query)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun searchProducts(query: String) = viewModelScope.launch {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
        productRepository
            .searchProducts(query)
            .onSuccess { searchResults ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = null,
                        searchResults = searchResults
                    )
                }
            }.onError { error ->
                _state.update {
                    it.copy(
                        searchResults = emptyList(),
                        isLoading = false,
                        errorMessage = error.toUiText()
                    )
                }
            }
    }

}