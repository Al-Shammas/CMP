package org.cmpbachelor.project.catalog.data.repository

import androidx.sqlite.SQLiteException
import coil3.util.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.cmpbachelor.project.catalog.data.database.FavoriteProductDao
import org.cmpbachelor.project.catalog.data.mappers.toProduct
import org.cmpbachelor.project.catalog.data.mappers.toProductEntity
import org.cmpbachelor.project.catalog.data.network.RemoteProductDataSource
import org.cmpbachelor.project.catalog.domain.Product
import org.cmpbachelor.project.catalog.domain.ProductRepository
import org.cmpbachelor.project.core.domain.DataError
import org.cmpbachelor.project.core.domain.EmptyResult
import org.cmpbachelor.project.core.domain.Result
import org.cmpbachelor.project.core.domain.map

class DefaultProductRepository(
    private val remoteBookDataSource: RemoteProductDataSource,
    private val favoriteProductDao: FavoriteProductDao,
) : ProductRepository {

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

    override fun getFavoriteProducts(): Flow<List<Product>> {
        return favoriteProductDao.getFavoriteProducts().map { productEntities ->
            productEntities.map { it.toProduct() }
        }
    }

    override fun isProductFavorite(id: Int): Flow<Boolean> {
        return favoriteProductDao
            .getFavoriteProducts()
            .map { productEntities ->
                productEntities.any { it.id == id }
            }
    }

    override suspend fun markAsFavorite(product: Product): EmptyResult<DataError.Local> {
        return try {
            favoriteProductDao.upsert(product = product.toProductEntity())
            Result.Success(Unit)
        } catch (e: SQLiteException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteFavorite(id: Int) {
        favoriteProductDao.deleteFavoriteProduct(id = id)
    }
}