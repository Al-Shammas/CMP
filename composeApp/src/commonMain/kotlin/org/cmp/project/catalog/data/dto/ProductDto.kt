package org.cmp.project.catalog.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    val id: Int,
    val title: String,
    val description: String? = null,
    val category: String? = null,
    val price: Double,
    val rating: Double?,
    val stock: Int,
    val thumbnail: String? = null,
)