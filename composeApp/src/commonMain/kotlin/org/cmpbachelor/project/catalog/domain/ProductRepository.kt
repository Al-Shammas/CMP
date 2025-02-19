package org.cmpbachelor.project.catalog.domain

import org.cmpbachelor.project.core.domain.DataError
import org.cmpbachelor.project.core.domain.Result


interface ProductRepository {
    suspend fun getProducts(): Result<List<Product>, DataError.Remote>
    suspend fun searchProducts(query: String): Result<List<Product>, DataError.Remote>
}