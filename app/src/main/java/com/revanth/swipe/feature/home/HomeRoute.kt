package com.revanth.swipe.feature.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.revanth.swipe.navigation.SplashScreen
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

fun NavGraphBuilder.homeDestination() {
    composable<HomeRoute> {
        HomeScreen()
    }
}