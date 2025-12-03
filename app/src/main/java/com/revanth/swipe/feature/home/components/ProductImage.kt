package com.revanth.swipe.feature.home.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.revanth.swipe.R

@Composable
fun ProductImage(
    imageUrl: String?,
    @DrawableRes loadingPlaceholder: Int = R.drawable.ic_launcher_background,
    @DrawableRes errorPlaceholder: Int = R.drawable.swipe,
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl?.takeIf { it.isNotBlank() })
            .crossfade(true)
            .build(),
        contentDescription = "Product Image",
        placeholder = painterResource(loadingPlaceholder),
        error = painterResource(errorPlaceholder),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(RoundedCornerShape(12.dp))
    )
}