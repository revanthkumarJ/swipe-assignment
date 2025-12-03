package com.revanth.swipe.feature.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.revanth.swipe.R
import com.revanth.swipe.core.ui.components.SwipeLottieAnimation

@Composable
fun EmptyContent(
    text: String
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier=Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SwipeLottieAnimation(
                raw = R.raw.empty,
                size = 250.dp
            )
            Text(text)
        }

    }
}

@Composable
fun SuccessComponent(
    text: String,
    size: Dp=150.dp,
    modifier: Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier=Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SwipeLottieAnimation(
                raw = R.raw.success,
                size = size
            )
            Text(text)
        }

    }
}

@Composable
fun FailureComponent(
    text: String,
    size: Dp=150.dp,
    modifier: Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier=Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SwipeLottieAnimation(
                raw = R.raw.error,
                size = size
            )
            Text(text)
        }

    }
}
