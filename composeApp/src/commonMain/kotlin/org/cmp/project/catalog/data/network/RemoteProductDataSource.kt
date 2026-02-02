package org.cmp.project.catalog.data.network

import org.cmp.project.catalog.data.dto.ProductsApiResponse
import org.cmp.project.core.domain.DataError
import org.cmp.project.core.domain.Result

interface RemoteProductDataSource {

    suspend fun getProducts(): Result<ProductsApiResponse, DataError.Remote>

    suspend fun searchProducts(
        query: String,
    ): Result<ProductsApiResponse, DataError.Remote>
}