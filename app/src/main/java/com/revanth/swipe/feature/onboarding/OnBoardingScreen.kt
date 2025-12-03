package com.revanth.swipe.feature.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import com.revanth.swipe.feature.onboarding.components.OnBoardingScreenPage
import org.koin.androidx.compose.koinViewModel
import com.revanth.swipe.R

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = koinViewModel(),
    onComplete: () -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    // Collect events
    LaunchedEffect(Unit) {
        viewModel.eventState.collect { event ->
            if (event is OnboardingEvent.OnboardingCompleted) {
                onComplete()
            }
        }
    }

    val onNext = { viewModel.handleAction(OnboardingAction.NextPage) }

    Scaffold { paddingValues ->
        AnimatedContent(
            targetState = state.currentPage,
            transitionSpec = {
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(300)
                ) togetherWith slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(300)
                )
            },
            modifier = Modifier.padding(paddingValues)
        ) { page ->

            when (page) {
                1 -> OnboardingScreenPageOne(
                    onNext = onNext,
                    currentPage = page,
                )

                2 -> OnboardingScreenPageTwo(
                    onNext = onNext,
                    currentPage = page,
                )

            }
        }
    }
}


@Composable
fun OnboardingScreenPageOne(
    onNext: () -> Unit,
    currentPage: Int,
) {
    OnBoardingScreenPage(
        onNext = onNext,
        currentPage = currentPage,
        title = "Welcome to Swipe Store",
        description = "See All Products, Search and Add Products to Server",
        raw = R.raw.see_products
    )
}

@Composable
fun OnboardingScreenPageTwo(
    onNext: () -> Unit,
    currentPage: Int,
) {
    OnBoardingScreenPage(
        onNext = onNext,
        currentPage = currentPage,
        title = "Offline Capability",
        description = "Can view Products and Add products offline, once network available it automatically syncs",
        raw = R.raw.sync
    )
}
