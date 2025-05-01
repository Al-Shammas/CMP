package org.cmpbachelor.project.catalog.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ProductEntity::class],
    version = 1
)

@ConstructedBy(ShoppingCartDatabaseConstructor::class)

abstract class ShoppingCartDatabase: RoomDatabase() {
    abstract val favoriteProductDao: FavoriteProductDao

    companion object{
        const val DB_NAME = "shoppingCart.db"
    }
}