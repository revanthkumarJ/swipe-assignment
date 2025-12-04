package com.revanth.swipe.navigation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import com.revanth.swipe.core.models.ThemeConfig
import com.revanth.swipe.core.ui.theme.SwipeTheme
import com.revanth.swipe.feature.home.HomeRoute
import com.revanth.swipe.feature.home.homeDestination
import com.revanth.swipe.feature.home.homeDetailsDestination
import com.revanth.swipe.feature.home.navigateToHome
import com.revanth.swipe.feature.home.navigateToHomeDetails
import com.revanth.swipe.feature.onboarding.OnBoardingRoute
import com.revanth.swipe.feature.onboarding.onBoardingDestination
import com.revanth.swipe.feature.settings.settings.navigateToSettingsRoute
import com.revanth.swipe.feature.settings.settings.settingsDestination
import com.revanth.swipe.feature.settings.theme.themeDestination
import org.koin.androidx.compose.koinViewModel

@Composable
fun RootNavScreen(
    modifier: Modifier = Modifier,
    viewModel: RootNavViewModel = koinViewModel(),
) {
    val navController = rememberNavController()

    val state by viewModel.state.collectAsStateWithLifecycle()
    val theme by viewModel.theme.collectAsStateWithLifecycle()

    val isDarkTheme = when (theme) {
        ThemeConfig.DARK -> true
        ThemeConfig.LIGHT -> false
        ThemeConfig.SYSTEM -> isSystemInDarkTheme()
    }

    val startDestination = when (state) {
        RootNavState.Home -> HomeRoute
        RootNavState.ShowOnboarding -> OnBoardingRoute
        RootNavState.Splash -> SplashRoute
    }

    SwipeTheme(
        darkTheme = isDarkTheme,
    ) {
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = modifier,
        ) {
            splashDestination()

            homeDestination(
                navigateToSettings = navController::navigateToSettingsRoute,
                navigateToDetailsScreen = navController::navigateToHomeDetails
            )

            homeDetailsDestination(
                navigateBack = navController::popBackStack
            )

            onBoardingDestination(
                onboardingComplete = navController::navigateToHome
            )

            settingsDestination(navController)
        }
    }
}