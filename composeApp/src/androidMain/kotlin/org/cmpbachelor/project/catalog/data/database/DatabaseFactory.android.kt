package org.cmpbachelor.project.catalog.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual class DatabaseFactory(private val context: Context) {
    actual fun create(): RoomDatabase.Builder<ShoppingCartDatabase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(ShoppingCartDatabase.DB_NAME)

        return Room.databaseBuilder(
            context = appContext,
            name = dbFile.absolutePath
        )
    }
}