package org.cmpbachelor.project.catalog.data.repository

import org.cmpbachelor.project.catalog.data.mappers.toProduct
import org.cmpbachelor.project.catalog.data.network.RemoteProductDataSource
import org.cmpbachelor.project.catalog.domain.Product
import org.cmpbachelor.project.catalog.domain.ProductRepository
import org.cmpbachelor.project.core.domain.DataError
import org.cmpbachelor.project.core.domain.Result
import org.cmpbachelor.project.core.domain.map

class DefaultProductRepository(private val remoteBookDataSource: RemoteProductDataSource) :
    ProductRepository {

    override suspend fun getProducts(): Result<List<Product>, DataError.Remote> {
        return remoteBookDataSource.getProducts().map { apiResponse ->
            apiResponse.products.map {
                it.toProduct()
            }

        }
    }

    override suspend fun searchProducts(query: String): Result<List<Product>, DataError.Remote> {
        return remoteBookDataSource.searchProducts(query).map { aipResponse ->
            aipResponse.products.map {
                it.toProduct()
            }
        }
    }
}