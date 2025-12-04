package com.revanth.swipe.feature.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.revanth.swipe.core.models.Product
import com.revanth.swipe.feature.onboarding.OnBoardingRoute
import com.revanth.swipe.navigation.SplashScreen
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

@Serializable
data class HomeDetailsRoute(
    val image: String? = null,
    val price: String? = null,
    val productName: String = "",
    val productType: String = "",
    val tax: String? = null
)

fun NavGraphBuilder.homeDestination(
    navigateToSettings: () -> Unit,
    navigateToDetailsScreen:(product:Product)-> Unit
) {
    composable<HomeRoute> {
        HomeScreen(
            navigateToSettings=navigateToSettings,
            navigateToDetailsScreen = navigateToDetailsScreen
        )
    }
}

fun NavGraphBuilder.homeDetailsDestination(
    navigateBack: () -> Unit
) {
    composable<HomeDetailsRoute> {
        val route= it.toRoute<HomeDetailsRoute>()
        ProductDetailScreen(
            onBackClick = navigateBack,
            product = Product(
                productName = route.productName,
                productType = route.productType,
                price = route.price?.toDoubleOrNull(),
                tax = route.tax?.toDoubleOrNull(),
                image = route.image
            )
        )
    }
}

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(route = HomeRoute, navOptions = navOptions)
}

fun NavController.navigateToHomeDetails(
    product: Product,
    navOptions: NavOptions? = null
) {
    this.navigate(route = HomeDetailsRoute(
        image = product.image,
        price = product.price.toString(),
        productName = product.productName,
        productType = product.productType,
        tax = product.tax.toString()
    ), navOptions = navOptions)
}