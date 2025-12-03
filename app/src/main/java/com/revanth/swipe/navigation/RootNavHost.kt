package com.revanth.swipe.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import com.revanth.swipe.feature.home.HomeRoute
import com.revanth.swipe.feature.home.homeDestination
import org.koin.androidx.compose.koinViewModel

@Composable
fun RootNavScreen(
    modifier: Modifier = Modifier,
    viewModel: RootNavViewModel = koinViewModel(),
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = HomeRoute,
        modifier = modifier,
    ) {
        splashDestination()

        homeDestination()
    }
}