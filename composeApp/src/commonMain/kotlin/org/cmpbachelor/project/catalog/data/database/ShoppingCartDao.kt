package org.cmpbachelor.project.catalog.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingCartDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(cartItem: CartItemEntity)

    @Query("SELECT * FROM CartItemEntity")
    fun getCartItems(): Flow<List<CartItemEntity>>

    @Query("SELECT * FROM CartItemEntity WHERE productId = :productId")
    suspend fun getCartItem(productId: Int): CartItemEntity?

    @Query("DELETE FROM CartItemEntity WHERE cartItemId = :cartItemId")
    suspend fun deleteByCartItemId(cartItemId: Long)

    @Query("DELETE FROM CartItemEntity WHERE productId = :productId")
    suspend fun deleteByProductId(productId: Int)

    @Update
    suspend fun update(cartItem: CartItemEntity)

    @Query("DELETE FROM CartItemEntity")
    suspend fun clearCart()
}
