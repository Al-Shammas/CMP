package org.cmp.project.catalog.data.database

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object ShoppingCartDatabaseConstructor: RoomDatabaseConstructor<ShoppingCartDatabase> {
    override fun initialize(): ShoppingCartDatabase
}