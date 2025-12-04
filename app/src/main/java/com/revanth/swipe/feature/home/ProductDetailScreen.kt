package com.revanth.swipe.feature.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import com.revanth.swipe.core.models.Product
import com.revanth.swipe.core.ui.components.SwipeScaffold
import com.revanth.swipe.R
import com.revanth.swipe.core.ui.theme.SwipeTheme

@Composable
fun ProductDetailScreen(
    product: Product,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    SwipeScaffold(
        topBarTitle = "Product Details",
        showNavigationIcon = true,
        onNavigateBack = onBackClick,
        modifier = modifier
    ) {
        ProductDetailContent(product = product)
    }
}

@Composable
fun ProductDetailContent(
    product: Product,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally // CENTER ALIGN EVERYTHING
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
                .clip(RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(product.image?.takeIf { it.isNotBlank() })
                    .crossfade(true)
                    .build(),
                contentDescription = "Product Image",
                placeholder = painterResource(R.drawable.loading),
                error = painterResource(R.drawable.swipe),
                modifier = modifier.fillMaxWidth()
            )
        }

        Text(
            text = product.productName,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        if (product.productType.isNotBlank()) {
            Text(
                text = product.productType,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }

        Divider(Modifier.padding(vertical = 12.dp))

        Text(
            text = "Price:",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            text = "₹${String.format("%.2f", product.price ?: 0.0)}",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        if (product.tax != null) {
            Text(
                text = "Tax: ${product.tax}%",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(Modifier.height(32.dp))
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
fun ProductDetailScreenPreview() {
    SwipeTheme {
        ProductDetailScreen(
            product = Product(
                productName = "Sample Product",
                productType = "Product",
                price = 299.9,
                tax = 18.9,
                image = null
            ),
            onBackClick = {}
        )
    }
}

