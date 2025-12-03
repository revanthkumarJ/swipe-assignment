package com.revanth.swipe.core.data.reposImpl

import com.revanth.swipe.core.common.DataState
import com.revanth.swipe.core.data.repos.ProductRepository
import com.revanth.swipe.core.models.AddProductResponse
import com.revanth.swipe.core.models.Product
import com.revanth.swipe.core.network.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ProductRepositoryImpl(
    private val api: ApiService
) : ProductRepository {

    override  fun getProducts(): Flow<DataState<List<Product>>> = flow {
        emit(DataState.Loading)

        try {
            val products = api.getProducts()

            if (products.isEmpty()) {
                emit(DataState.Empty)
            } else {
                emit(DataState.Success(products))
            }

        } catch (e: Exception) {
            emit(DataState.Error(e.localizedMessage ?: "Unknown error", e))
        }
    }

    override suspend fun addProduct(
        productName: RequestBody,
        productType: RequestBody,
        price: RequestBody,
        tax: RequestBody,
        image: MultipartBody.Part?
    ): DataState<AddProductResponse> {

        return try {
            val response = api.addProduct(
                productName = productName,
                productType = productType,
                price = price,
                tax = tax,
                files = image
            )

            if (response.success) {
                DataState.Success(response)
            } else {
                DataState.Error(response.message)
            }

        } catch (e: Exception) {
            DataState.Error(e.localizedMessage ?: "Unknown error", e)
        }
    }
}