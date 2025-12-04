package com.revanth.swipe.feature.settings.theme

import com.revanth.swipe.feature.onboarding.OnboardingViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val ThemeModule = module {
    viewModelOf(::ChangeThemeViewModel)
}