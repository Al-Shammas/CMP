package org.cmp.project.catalog.data.mappers

import org.cmp.project.catalog.data.database.ProductEntity
import org.cmp.project.catalog.data.dto.ProductDto
import org.cmp.project.catalog.domain.Product

fun ProductDto.toProduct(): Product {
    return Product(
        id = id,
        title = title,
        description = description,
        category = category,
        price = price,
        rating = rating,
        stock = stock,
        thumbnail = thumbnail
    )
}

fun Product.toProductEntity(): ProductEntity {
    return ProductEntity(
        id = id,
        title = title,
        description = description,
        category = category,
        price = price,
        rating = rating,
        stock = stock,
        thumbnail = thumbnail
    )
}

fun ProductEntity.toProduct(): Product {
    return Product(
        id = id,
        title = title,
        description = description,
        category = category,
        price = price,
        rating = rating,
        stock = stock,
        thumbnail = thumbnail
    )
}