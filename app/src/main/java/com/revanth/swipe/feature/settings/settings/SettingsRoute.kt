package com.revanth.swipe.feature.settings.settings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
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
            navigateBack = { navController.popBackStack() },
            navigateToScreen = { it->
                when(it){
                    SettingsItems.AboutUs -> TODO()
                    SettingsItems.AppInfo -> TODO()
                    SettingsItems.ChangeTheme -> TODO()
                    SettingsItems.FAQ -> TODO()
                    SettingsItems.Help -> TODO()
                }
            }
        )
    }
}

fun NavGraphBuilder.settingsRoute(
    navigateBack: () -> Unit,
    navigateToScreen: (SettingsItems) -> Unit,
) {
    composable<SettingsRoute> {
        SettingsScreen(

        )
    }
}