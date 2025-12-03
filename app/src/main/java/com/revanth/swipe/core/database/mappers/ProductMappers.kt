package com.revanth.swipe.core.database.mappers

import com.revanth.swipe.core.database.enitities.ProductEntity
import com.revanth.swipe.core.models.Product

fun Product.toProductEntity(): ProductEntity {
    return ProductEntity(
        productName = productName,
        productType = productType,
        price = price,
        tax = tax,
        image = image
    )
}

fun ProductEntity.toProduct(): Product {
    return Product(
        productName = productName,
        productType = productType,
        price = price,
        tax = tax,
        image = image
    )
}