package com.revanth.swipe.core.data.repos

import com.revanth.swipe.core.common.DataState
import com.revanth.swipe.core.models.AddProductResponse
import com.revanth.swipe.core.models.Product
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface ProductRepository {

    suspend fun getProducts(): Flow<DataState<List<Product>>>

    suspend fun addProduct(
        productName: RequestBody,
        productType: RequestBody,
        price: RequestBody,
        tax: RequestBody,
        image: MultipartBody.Part?
    ): DataState<AddProductResponse>
}