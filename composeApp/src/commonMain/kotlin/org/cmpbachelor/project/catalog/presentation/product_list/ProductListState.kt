package org.cmpbachelor.project.catalog.presentation.product_list

import org.cmpbachelor.project.catalog.domain.Product
import org.cmpbachelor.project.core.presentation.UiText

data class ProductListState(
    val searchQuery: String = "",
    val searchResults: List<Product> = emptyList(),
    val productListResults: List<Product> = emptyList(),
    val favoriteProducts: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val selectedTabIndex: Int = 0,
    val errorMessage: UiText? = null,
    val selectedProduct: Product? = null,
)
