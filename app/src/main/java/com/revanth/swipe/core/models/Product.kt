package com.revanth.swipe.core.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val image: String? = null,
    val price: Double? = 0.0,

    @SerialName("product_name")
    val productName: String = "",

    @SerialName("product_type")
    val productType: String = "",

    val tax: Double? = 0.0
)