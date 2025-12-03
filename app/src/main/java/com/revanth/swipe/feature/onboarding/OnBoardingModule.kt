package com.revanth.swipe.feature.onboarding

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val OnboardingModule = module {
    viewModelOf(::OnboardingViewModel)
}