package org.cmp.project.catalog.domain

data class Product(
    val id: Int,
    val title: String,
    val description: String? = null,
    val category: String? = null,
    val price: Double,
    val rating: Double?,
    val stock: Int?,
    val thumbnail: String? = null,
    )