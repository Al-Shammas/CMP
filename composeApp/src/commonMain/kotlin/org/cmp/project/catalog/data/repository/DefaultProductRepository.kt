package org.cmp.project.catalog.data.repository

import androidx.sqlite.SQLiteException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.cmp.project.catalog.data.database.FavoriteProductDao
import org.cmp.project.catalog.data.mappers.toProduct
import org.cmp.project.catalog.data.mappers.toProductEntity
import org.cmp.project.catalog.data.network.RemoteProductDataSource
import org.cmp.project.catalog.domain.Product
import org.cmp.project.catalog.domain.ProductRepository
import org.cmp.project.core.domain.DataError
import org.cmp.project.core.domain.EmptyResult
import org.cmp.project.core.domain.Result
import org.cmp.project.core.domain.map

class DefaultProductRepository(
    private val remoteProductDataSource: RemoteProductDataSource,
    private val favoriteProductDao: FavoriteProductDao,
) : ProductRepository {

    override suspend fun getProducts(): Result<List<Product>, DataError.Remote> {
        return remoteProductDataSource.getProducts().map { apiResponse ->
            apiResponse.products.map {
                it.toProduct()
            }

        }
    }

    override suspend fun searchProducts(query: String): Result<List<Product>, DataError.Remote> {
        return remoteProductDataSource.searchProducts(query).map { aipResponse ->
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