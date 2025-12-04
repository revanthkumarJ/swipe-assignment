package com.revanth.swipe.feature.settings.settings

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class SettingsItems(
    val title: String,
    val subTitle: String = "",
    val icon: ImageVector,
) {

    data object ChangeTheme : SettingsItems(
        title = "Change Theme",
        subTitle = "Switch between light & dark mode",
        icon = Icons.Default.DarkMode
    )

    data object AboutUs : SettingsItems(
        title = "About Us",
        subTitle = "Know more about our company",
        icon = Icons.Default.Info
    )

    data object Help : SettingsItems(
        title = "Help & Support",
        subTitle = "Get assistance with issues",
        icon = Icons.Default.Help
    )

    data object FAQ : SettingsItems(
        title = "FAQ",
        subTitle = "Frequently asked questions",
        icon = Icons.Default.QuestionAnswer
    )

    data object AppInfo : SettingsItems(
        title = "App Info",
        subTitle = "Version, build & permissions",
        icon = Icons.Default.Settings
    )
}

internal val settingsItems: List<SettingsItems> = listOf(
    SettingsItems.ChangeTheme,
    SettingsItems.Help,
    SettingsItems.FAQ,
    SettingsItems.AboutUs,
    SettingsItems.AppInfo
)
