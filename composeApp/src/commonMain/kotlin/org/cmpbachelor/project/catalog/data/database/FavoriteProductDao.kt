package org.cmpbachelor.project.catalog.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteProductDao {

    @Upsert
    suspend fun upsert(product: ProductEntity)

    @Query("SELECT * FROM ProductEntity")
    fun getFavoriteProducts(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM ProductEntity WHERE id = :id")
    fun getFavoriteProduct(id: Int): Flow<ProductEntity>?

    @Query("DELETE FROM ProductEntity WHERE id = :id")
    suspend fun deleteFavoriteProduct(id: Int)
}