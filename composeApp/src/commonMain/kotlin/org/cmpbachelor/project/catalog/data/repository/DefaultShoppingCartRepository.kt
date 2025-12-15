package org.cmpbachelor.project.catalog.data.repository

import kotlinx.coroutines.flow.Flow
import org.cmpbachelor.project.catalog.data.database.CartItemEntity
import org.cmpbachelor.project.catalog.data.database.ShoppingCartDao
import org.cmpbachelor.project.catalog.domain.ShoppingCartRepository

class DefaultShoppingCartRepository(
    private val shoppingCartDao: ShoppingCartDao
) : ShoppingCartRepository {

    override suspend fun addProductToCart(entity: CartItemEntity) {
        val existing: CartItemEntity? = shoppingCartDao.getCartItem(entity.productId)
        if (existing != null) {
            val updated = existing.copy(quantity = existing.quantity + 1)
            shoppingCartDao.upsert(updated)
        } else {
            shoppingCartDao.upsert(entity)
        }
    }

    override suspend fun removeOneFromCart(productId: Int) {
        val existing = shoppingCartDao.getCartItem(productId)
        if (existing != null) {
            if (existing.quantity > 1) {
                val updated = existing.copy(quantity = existing.quantity - 1)
                shoppingCartDao.upsert(updated)
            } else {
                shoppingCartDao.deleteByProductId(productId)
            }
        }
    }

    override suspend fun deleteProductFromCart(productId: Int) {
        shoppingCartDao.deleteByProductId(productId)
    }

    override fun getCartItems(): Flow<List<CartItemEntity>> {
        return shoppingCartDao.getCartItems()
    }

    override suspend fun clearCart() {
        shoppingCartDao.clearCart()
    }
}