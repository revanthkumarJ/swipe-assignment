package com.revanth.swipe.core.ui.components

import androidx.annotation.RawRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun SwipeLottieAnimation(
    @RawRes raw: Int,
    modifier: Modifier = Modifier,
    size: Dp = 80.dp,
    iterations: Int = LottieConstants.IterateForever,
) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(raw)
    )

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = iterations
    )

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier.size(size),
        contentScale = ContentScale.Fit
    )
}

@Composable
fun SwipeLottieAnimationOverlay(
    @RawRes raw: Int,
    modifier: Modifier = Modifier.fillMaxSize()
) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(raw)
    )

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    Box(
        modifier = modifier
            .background(Color.Black.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress }
        )
    }
}
