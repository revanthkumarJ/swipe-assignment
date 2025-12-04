package com.revanth.swipe.feature.settings.theme

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object ThemeRoute

internal fun NavGraphBuilder.themeDestination(
    navigateBack: () -> Unit,
) {
    composable<ThemeRoute> {
        ChangeThemeScreen(
            onNavigateBack = navigateBack
        )
    }
}


internal fun NavController.navigateToTheme(navOptions: NavOptions? = null) =
    navigate(ThemeRoute, navOptions)