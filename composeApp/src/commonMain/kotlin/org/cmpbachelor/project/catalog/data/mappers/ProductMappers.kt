package org.cmpbachelor.project.catalog.data.mappers

import org.cmpbachelor.project.catalog.data.database.ProductEntity
import org.cmpbachelor.project.catalog.data.dto.ProductDto
import org.cmpbachelor.project.catalog.domain.Product

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