package org.cmp.project.catalog.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ProductEntity::class, CartItemEntity::class],
    version = 2
)

@ConstructedBy(ShoppingCartDatabaseConstructor::class)

abstract class ShoppingCartDatabase: RoomDatabase() {
    abstract val favoriteProductDao: FavoriteProductDao
    abstract val shoppingCartDao: ShoppingCartDao

    companion object{
        const val DB_NAME = "shoppingCart.db"
    }
}