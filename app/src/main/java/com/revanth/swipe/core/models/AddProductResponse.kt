package com.revanth.swipe.core.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddProductResponse(
    val message: String,

    @SerialName("product_details")
    val productDetails: Product? = null,

    @SerialName("product_id")
    val productId: Int,

    val success: Boolean
)