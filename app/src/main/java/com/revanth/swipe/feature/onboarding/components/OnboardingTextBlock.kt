package com.revanth.swipe.feature.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.revanth.swipe.feature.onboarding.Total_Pages


@Composable
fun OnboardingTextBlock(
    onNext: () -> Unit,
    currentPage: Int,
    modifier: Modifier = Modifier,
    totalPages: Int = Total_Pages,
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(vertical = 32.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            repeat(totalPages) { index ->
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(
                            color = if (index == currentPage - 1) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                            },
                            shape = CircleShape,
                        ),
                )
                if (index < totalPages - 1) Spacer(Modifier.width(8.dp))
            }
        }

        Button(
            modifier = Modifier.width(60.dp),
            onClick = onNext,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = MaterialTheme.shapes.medium,
        ) {
            Icon(
                imageVector = Icons.Default.ArrowForwardIos,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Preview
@Composable
fun PreviewOnboardingTextBlock() {
    OnboardingTextBlock(
        onNext = {},
        currentPage = 2,
        totalPages = 5,
    )
}