package com.revanth.swipe.core.data.repos

import com.revanth.swipe.core.database.enitities.ProductEntity
import com.revanth.swipe.core.database.enitities.UnsyncedProductEntity
import kotlinx.coroutines.flow.Flow

interface ProductLocalRepository {
    fun getAllProductsFlow(): Flow<List<ProductEntity>>
    fun getAllUnsyncedProductsFlow(): Flow<List<UnsyncedProductEntity>>

    suspend fun updateProducts(newList: List<ProductEntity>)
    suspend fun addUnsyncedProduct(product: UnsyncedProductEntity)
    suspend fun deleteUnsyncedProduct(product: UnsyncedProductEntity)
}
