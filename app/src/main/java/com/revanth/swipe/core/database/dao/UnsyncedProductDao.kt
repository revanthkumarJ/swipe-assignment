package com.revanth.swipe.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.revanth.swipe.core.database.enitities.UnsyncedProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UnsyncedProductDao {

    @Query("SELECT * FROM unsynced_products")
    fun getAllUnsyncedProducts(): Flow<List<UnsyncedProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnsyncedProduct(product: UnsyncedProductEntity)

    @Delete
    suspend fun deleteUnsyncedProduct(product: UnsyncedProductEntity)
}