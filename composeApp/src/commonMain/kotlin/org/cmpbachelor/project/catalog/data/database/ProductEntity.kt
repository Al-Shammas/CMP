package org.cmpbachelor.project.catalog.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductEntity (
    @PrimaryKey(autoGenerate = false) val id: Int,
    val title: String,
    val description: String?,
    val category: String?,
    val price: Double,
    val rating: Double?,
    val stock: Int?,
    val thumbnail: String?,
)