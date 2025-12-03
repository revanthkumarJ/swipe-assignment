package com.revanth.swipe.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import com.revanth.swipe.feature.home.HomeRoute
import com.revanth.swipe.feature.home.homeDestination
import com.revanth.swipe.feature.home.navigateToHome
import com.revanth.swipe.feature.onboarding.OnBoardingRoute
import com.revanth.swipe.feature.onboarding.onBoardingDestination
import org.koin.androidx.compose.koinViewModel

@Composable
fun RootNavScreen(
    modifier: Modifier = Modifier,
    viewModel: RootNavViewModel = koinViewModel(),
) {
    val navController = rememberNavController()

    val state by viewModel.state.collectAsStateWithLifecycle()

    val startDestination = when (state) {
        RootNavState.Home -> HomeRoute
        RootNavState.ShowOnboarding -> OnBoardingRoute
        RootNavState.Splash -> SplashRoute
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        splashDestination()

        homeDestination(
            navigateToSettings = {}
        )

        onBoardingDestination(
            onboardingComplete = navController::navigateToHome
        )
    }
}