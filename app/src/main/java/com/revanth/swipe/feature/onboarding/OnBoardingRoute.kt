package com.revanth.swipe.feature.onboarding

import kotlinx.serialization.Serializable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

@Serializable
data object OnBoardingRoute


fun NavController.navigateToOnboarding(navOptions: NavOptions? = null) {
    this.navigate(route = OnBoardingRoute, navOptions = navOptions)
}

fun NavGraphBuilder.onBoardingDestination(
    onboardingComplete: () -> Unit
) {
    composable<OnBoardingRoute> {
        OnboardingScreen(
            onComplete = onboardingComplete
        )
    }
}