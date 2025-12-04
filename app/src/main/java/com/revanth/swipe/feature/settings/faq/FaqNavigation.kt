package com.revanth.swipe.feature.settings.faq

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object FaqRoute

fun NavGraphBuilder.faqRoute(
    navigateBack: () -> Unit,
) {
    composable<FaqRoute> {
        FaqScreen(
            onNavigateBack = navigateBack
        )
    }
}

fun NavController.navigateToFAQ(navOptions: NavOptions? = null) {
    this.navigate(FaqRoute, navOptions)
}