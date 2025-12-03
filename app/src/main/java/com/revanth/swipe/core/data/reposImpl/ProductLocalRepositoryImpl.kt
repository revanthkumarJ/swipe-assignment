package com.revanth.swipe.core.data.reposImpl

import com.revanth.swipe.core.data.repos.ProductLocalRepository
import com.revanth.swipe.core.database.dao.ProductDao
import com.revanth.swipe.core.database.dao.UnsyncedProductDao
import com.revanth.swipe.core.database.enitities.ProductEntity
import com.revanth.swipe.core.database.enitities.UnsyncedProductEntity
import kotlinx.coroutines.flow.Flow

class ProductLocalRepositoryImpl(
    private val productDao: ProductDao,
    private val unsyncedProductDao: UnsyncedProductDao
) : ProductLocalRepository {

    override fun getAllProductsFlow(): Flow<List<ProductEntity>> =
        productDao.getAllProducts()

    override fun getAllUnsyncedProductsFlow(): Flow<List<UnsyncedProductEntity>> =
        unsyncedProductDao.getAllUnsyncedProducts()

    override suspend fun updateProducts(newList: List<ProductEntity>) {
        productDao.deleteAllProducts()
        productDao.insertProducts(newList)
    }

    override suspend fun addUnsyncedProduct(product: UnsyncedProductEntity) {
        unsyncedProductDao.insertUnsyncedProduct(product)
    }

    override suspend fun deleteUnsyncedProduct(product: UnsyncedProductEntity) {
        unsyncedProductDao.deleteUnsyncedProduct(product)
    }
}
