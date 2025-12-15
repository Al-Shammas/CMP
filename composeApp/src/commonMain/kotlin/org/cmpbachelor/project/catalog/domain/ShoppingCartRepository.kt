package org.cmpbachelor.project.catalog.domain

import kotlinx.coroutines.flow.Flow
import org.cmpbachelor.project.catalog.data.database.CartItemEntity

interface ShoppingCartRepository {

    suspend fun addProductToCart(entity: CartItemEntity)
    suspend fun removeOneFromCart(productId: Int)
    suspend fun deleteProductFromCart(productId: Int)
    fun getCartItems(): Flow<List<CartItemEntity>>
    suspend fun clearCart()
}