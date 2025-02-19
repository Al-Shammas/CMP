package org.cmpbachelor.project.catalog.data.network

import org.cmpbachelor.project.catalog.data.dto.ProductsApiResponse
import org.cmpbachelor.project.core.data.safeCall
import org.cmpbachelor.project.core.domain.DataError
import org.cmpbachelor.project.core.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val BASE_URL = "https://dummyjson.com"

class KtorRemoteProductDataSource(
    private val httpClient: HttpClient,
): RemoteProductDataSource {
    override suspend fun getProducts(): Result<ProductsApiResponse, DataError.Remote> {
        return safeCall<ProductsApiResponse> {
            httpClient.get(
                urlString = "$BASE_URL/products"
            )
        }
    }

    override suspend fun searchProducts(
        query: String,
    ): Result<ProductsApiResponse, DataError.Remote> {
        return safeCall<ProductsApiResponse> {
            httpClient.get(
                urlString = "$BASE_URL/products/search"
            ) {
                parameter("q", query)
            }
        }
    }
}