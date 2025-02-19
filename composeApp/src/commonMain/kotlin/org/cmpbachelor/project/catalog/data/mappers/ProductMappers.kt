package org.cmpbachelor.project.catalog.data.mappers

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