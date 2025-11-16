package org.cmpbachelor.project.catalog.presentation.product_details

import org.cmpbachelor.project.catalog.domain.Product


sealed interface ProductDetailAction{
    data object OnBackClick: ProductDetailAction
    data object OnFavoriteClick: ProductDetailAction
    data class OnSelectedProductChange(val product: Product): ProductDetailAction
    data object AddToShoppingCart : ProductDetailAction
}