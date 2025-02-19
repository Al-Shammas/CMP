package org.cmpbachelor.project.catalog.data.network

import org.cmpbachelor.project.catalog.data.dto.ProductsApiResponse
import org.cmpbachelor.project.core.domain.DataError
import org.cmpbachelor.project.core.domain.Result

interface RemoteProductDataSource {

    suspend fun getProducts(): Result<ProductsApiResponse, DataError.Remote>

    suspend fun searchProducts(
        query: String,
    ): Result<ProductsApiResponse, DataError.Remote>
}