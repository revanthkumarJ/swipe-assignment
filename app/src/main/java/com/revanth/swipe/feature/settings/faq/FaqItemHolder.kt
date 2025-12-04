package com.revanth.swipe.feature.settings.faq

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp

@Composable
fun FaqItemHolder(
    index: Int,
    isSelected: Boolean,
    onItemSelected: (Int) -> Unit,
    question: String?,
    answer: String?,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.medium,
            ),
    ) {
        Row(
            modifier = Modifier
                .clickable {
                    onItemSelected.invoke(index)
                }
                .padding(all = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = question.orEmpty(),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                color = MaterialTheme.colorScheme.primary,
            )

            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "drop down",
                modifier = Modifier
                    .scale(1f, if (isSelected) -1f else 1f),
            )
        }

        AnimatedVisibility(
            visible = isSelected,
            enter = fadeIn() + expandVertically(
                animationSpec = spring(
                    stiffness = Spring.StiffnessMedium,
                ),
            ),
        ) {
            Text(
                text = answer.orEmpty(),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 12.dp),
            )
        }

        HorizontalDivider()
    }
}
