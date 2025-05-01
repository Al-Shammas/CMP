package org.cmpbachelor.project.catalog.data.database

import androidx.room.RoomDatabaseConstructor
import org.cmpbachelor.project.catalog.data.database.ShoppingCartDatabase

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object ShoppingCartDatabaseConstructor: RoomDatabaseConstructor<ShoppingCartDatabase> {
    override fun initialize(): ShoppingCartDatabase
}