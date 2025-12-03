package com.revanth.swipe.core.database.enitities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "unsynced_products")
data class UnsyncedProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productName: String,
    val productType: String,
    val price: String,
    val tax: String,
    val imagePath: String? = null
)