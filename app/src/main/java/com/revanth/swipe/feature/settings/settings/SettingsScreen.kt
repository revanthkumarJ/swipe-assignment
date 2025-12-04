package com.revanth.swipe.feature.settings.settings

import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.revanth.swipe.core.ui.components.SwipeScaffold
import com.revanth.swipe.core.ui.theme.SwipeTheme

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    navigateToScreen: (SettingsItems) -> Unit,
) {
    SwipeScaffold(
        topBarTitle = "Settings",
        showNavigationIcon = true,
        onNavigateBack = onNavigateBack,
        modifier = modifier
    ) {
        val context = LocalContext.current
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            settingsItems.forEach { item ->
                SettingsScreenCard(
                    item = item,
                    onClick = {
                        when(item){
                            SettingsItems.AppInfo -> {
                                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                    data = Uri.fromParts("package", context.packageName, null)
                                }
                                context.startActivity(intent)
                            }

                            SettingsItems.AboutUs -> {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://getswipe.in"))
                                context.startActivity(intent)
                            }

                            else -> navigateToScreen(item)
                        }
                    }
                )
            }
        }
    }
}


@Composable
private fun SettingsScreenCard(
    item: SettingsItems,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    cardShape: RoundedCornerShape = RoundedCornerShape(16.dp),
    iconSize: Dp = 44.dp,
    iconShape: RoundedCornerShape = RoundedCornerShape(12.dp),
    titleStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    titleColor: Color = MaterialTheme.colorScheme.onSurface,
    subtitleStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    subtitleColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    trailingIconColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    overlayColor: Color = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
) {
    OutlinedCard(
        modifier = modifier.fillMaxWidth(),
        onClick = {
            onClick()
        },
        shape = cardShape,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
            disabledContainerColor = overlayColor,
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(iconSize)
                    .background(
                        brush =
                            Brush.linearGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.surfaceContainer,
                                    MaterialTheme.colorScheme.surfaceContainer,
                                ),
                            ),
                        shape = iconShape,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                Text(
                    text = item.title,
                    style = titleStyle,
                    color = titleColor,
                    fontWeight = FontWeight.Medium,
                )
                Text(
                    text = item.subTitle,
                    style = subtitleStyle,
                    color = subtitleColor,
                )
            }

            Icon(
                imageVector = Icons.Default.ArrowForwardIos,
                contentDescription = null,
                tint = trailingIconColor,
                modifier = Modifier.size(16.dp),
            )
        }
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
fun SettingsScreenPreview() {
    SwipeTheme {
        SettingsScreen(
            onNavigateBack = {},
            navigateToScreen = {}
        )
    }
}
