package org.cmp.project.catalog.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true) val cartItemId: Int = 0,
    val productId: Int,
    val title: String,
    val price: Double,
    val quantity: Int = 1,
    val thumbnail: String?
)
