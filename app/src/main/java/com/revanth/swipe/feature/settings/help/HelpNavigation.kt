package com.revanth.swipe.feature.settings.help

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object HelpRoute

internal fun NavController.navigateToHelp(navOptions: NavOptions? = null) =
    navigate(HelpRoute, navOptions)


internal fun NavGraphBuilder.helpDestination(
    onBackClick: () -> Unit,
    navigateToFAQ: () -> Unit,
) {
    composable<HelpRoute> {
        HelpScreen(
            onBackClick = onBackClick,
            navigateToFAQ = navigateToFAQ,
        )
    }
}