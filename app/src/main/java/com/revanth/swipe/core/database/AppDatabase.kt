package com.revanth.swipe.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.revanth.swipe.core.database.dao.ProductDao
import com.revanth.swipe.core.database.dao.UnsyncedProductDao
import com.revanth.swipe.core.database.enitities.ProductEntity
import com.revanth.swipe.core.database.enitities.UnsyncedProductEntity

@Database(
    entities = [
        ProductEntity::class,
        UnsyncedProductEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun unsyncedProductDao(): UnsyncedProductDao
}