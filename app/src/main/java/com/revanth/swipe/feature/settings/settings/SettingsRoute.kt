package com.revanth.swipe.feature.settings.settings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.revanth.swipe.feature.settings.faq.faqRoute
import com.revanth.swipe.feature.settings.faq.navigateToFAQ
import com.revanth.swipe.feature.settings.help.helpDestination
import com.revanth.swipe.feature.settings.help.navigateToHelp
import com.revanth.swipe.feature.settings.theme.navigateToTheme
import com.revanth.swipe.feature.settings.theme.themeDestination
import kotlinx.serialization.Serializable

@Serializable
data object SettingsRoute

@Serializable
data object SettingsGraph

fun NavController.navigateToSettingsRoute(navOptions: NavOptions? = null) {
    this.navigate(SettingsGraph, navOptions)
}

fun NavGraphBuilder.settingsDestination(
    navController: NavController,
) {
    navigation<SettingsGraph>(
        startDestination = SettingsRoute,
    ) {
        settingsRoute(
            navigateBack = navController::popBackStack,
            navigateToScreen = { it->
                when(it){
                    SettingsItems.ChangeTheme -> {
                        navController.navigateToTheme()
                    }
                    SettingsItems.FAQ -> {
                        navController.navigateToFAQ()
                    }
                    SettingsItems.Help -> {
                        navController.navigateToHelp()
                    }
                    else -> {}
                }
            }
        )

        faqRoute(
            navigateBack = navController::popBackStack,
        )

        helpDestination(
            onBackClick = navController::popBackStack,
            navigateToFAQ = navController::navigateToFAQ,
        )

        themeDestination(navController::popBackStack)
    }
}

fun NavGraphBuilder.settingsRoute(
    navigateBack: () -> Unit,
    navigateToScreen: (SettingsItems) -> Unit,
) {
    composable<SettingsRoute> {
        SettingsScreen(
            onNavigateBack = navigateBack,
            navigateToScreen = navigateToScreen,
        )
    }
}