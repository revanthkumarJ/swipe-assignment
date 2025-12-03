package com.revanth.swipe.feature.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.revanth.swipe.feature.onboarding.OnBoardingRoute
import com.revanth.swipe.navigation.SplashScreen
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

fun NavGraphBuilder.homeDestination(
    navigateToSettings: () -> Unit
) {
    composable<HomeRoute> {
        HomeScreen(
            navigateToSettings=navigateToSettings
        )
    }
}

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(route = HomeRoute, navOptions = navOptions)
}