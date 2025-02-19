package org.cmpbachelor.project.catalog.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductsApiResponse(
    val products: List<ProductDto>
)