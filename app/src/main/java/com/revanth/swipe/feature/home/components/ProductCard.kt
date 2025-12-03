package com.revanth.swipe.feature.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.revanth.swipe.core.models.Product

@Composable
fun ProductCard(
    product: Product,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column {
            ProductImage(
                imageUrl = product.image,
            )

            Column(modifier = Modifier.padding(12.dp)) {
                Text(product.productName, style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(6.dp))
                Text("₹ ${product.price}")
                Text("Type: ${product.productType}")
                Text("Tax: ${product.tax}%")
            }
        }
    }
}