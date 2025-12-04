package com.revanth.swipe.feature.settings.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.revanth.swipe.core.ui.components.SwipeButton
import com.revanth.swipe.core.ui.components.SwipeScaffold
import com.revanth.swipe.core.ui.theme.AppColors
import org.koin.androidx.compose.koinViewModel
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.ui.semantics.clearAndSetSemantics

@Composable
internal fun ChangeThemeScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewmodel: ChangeThemeViewModel = koinViewModel(),
) {
    val uiState by viewmodel.state.collectAsStateWithLifecycle()

    ThemeScreenContent(
        uiState = uiState,
        modifier = modifier,
        onAction = {
            viewmodel.handleAction(it)
        },
        navigateBack = {
            onNavigateBack()
        }
    )
}

@Composable
internal fun ThemeScreenContent(
    uiState: ThemeState,
    modifier: Modifier = Modifier,
    navigateBack:()->Unit,
    onAction: (ThemeAction) -> Unit,
) {
    SwipeScaffold(
        modifier = modifier.fillMaxSize(),
        topBarTitle = "Theme",
        onNavigateBack = {
            navigateBack()
        },
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            uiState.themeOptions.forEach { (theme, label) ->
                SwipeRadioButton(
                    label = label,
                    modifier = Modifier.fillMaxWidth(),
                    selected = uiState.currentTheme == theme,
                    onClick = { onAction(ThemeAction.ThemeSelection(theme)) },
                )
            }

            SwipeButton(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    onAction(ThemeAction.SetTheme)
                },
                text = "Apply Theme"
            )
        }
    }
}

@Composable
fun SwipeRadioButton(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isError: Boolean = false,
    contentDescription: String? = null,
    selectedColor: Color = AppColors.primaryBlue,
    unselectedColor: Color = AppColors.borderColor,
    errorColor: Color = MaterialTheme.colorScheme.error,
    selectedTextStyle: TextStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
    unselectedTextStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    borderWidth: Dp = 1.dp,
    animationDurationMs: Int = 200,
) {

    val animatedBorderColor by animateColorAsState(
        targetValue = when {
            isError -> errorColor
            selected -> selectedColor
            else -> AppColors.borderColorOne
        },
        animationSpec = tween(animationDurationMs),
        label = "border_color_animation",
    )

    val animatedRadioColor by animateColorAsState(
        targetValue = when {
            isError -> errorColor
            selected -> selectedColor
            else -> unselectedColor
        },
        animationSpec = tween(animationDurationMs),
        label = "radio_color_animation",
    )

    // Animated scale for selection feedback
    val animatedScale by animateFloatAsState(
        targetValue = if (selected) 1.02f else 1.0f,
        animationSpec = tween(animationDurationMs),
        label = "scale_animation",
    )

    val textStyle = if (selected) selectedTextStyle else unselectedTextStyle
    val interactionSource = remember { MutableInteractionSource() }

    // Semantic state description for accessibility
    val stateDescription = when {
        isError -> "Error state"
        selected -> "Selected"
        else -> "Not selected"
    }

    Box(
        modifier = Modifier
            .scale(animatedScale)
            .semantics(mergeDescendants = true) {
                this.contentDescription = contentDescription ?: "Radio button for $label"
                this.stateDescription = stateDescription
            },
    ) {
        Row(
            modifier = modifier
                .padding(vertical = 12.dp)
                .border(
                    width = borderWidth,
                    color = animatedBorderColor.copy(
                        alpha = if (enabled) 1.0f else 0.6f,
                    ),
                    shape = RoundedCornerShape(12.dp),
                )
                .clickable(
                    enabled = enabled,
                    onClick = onClick,
                    role = Role.RadioButton,
                    interactionSource = interactionSource,
                    indication = ripple(
                        bounded = true,
                        radius = 24.dp,
                    ),
                )
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            RadioButton(
                selected = selected,
                // Handled by the Row's clickable
                onClick = null,
                enabled = enabled,
                colors = RadioButtonColors(
                    selectedColor = animatedRadioColor,
                    unselectedColor = animatedRadioColor,
                    disabledSelectedColor = animatedRadioColor.copy(alpha = 0.6f),
                    disabledUnselectedColor = animatedRadioColor.copy(alpha = 0.6f),
                ),
                modifier = Modifier.clearAndSetSemantics { },
            )

            Text(
                text = label,
                style = textStyle,
                modifier = Modifier.clearAndSetSemantics { },
            )
        }
    }
}
