package org.cmp.project.catalog.presentation.product_list

import org.cmp.project.catalog.domain.Product

sealed interface ProductListAction {
    data class OnSearchQueryChange(val query: String): ProductListAction
    data class OnProductClick (val product: Product): ProductListAction
    data class OnTabSelected(val index: Int): ProductListAction
}