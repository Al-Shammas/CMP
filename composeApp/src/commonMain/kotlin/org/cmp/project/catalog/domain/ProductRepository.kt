package org.cmp.project.catalog.domain

import kotlinx.coroutines.flow.Flow
import org.cmp.project.core.domain.DataError
import org.cmp.project.core.domain.EmptyResult
import org.cmp.project.core.domain.Result


interface ProductRepository {
    suspend fun getProducts(): Result<List<Product>, DataError.Remote>
    suspend fun searchProducts(query: String): Result<List<Product>, DataError.Remote>

    suspend fun markAsFavorite(product: Product): EmptyResult<DataError.Local>
    fun getFavoriteProducts(): Flow<List<Product>>
    fun isProductFavorite(id: Int): Flow<Boolean>
    suspend fun deleteFavorite(id: Int)
}