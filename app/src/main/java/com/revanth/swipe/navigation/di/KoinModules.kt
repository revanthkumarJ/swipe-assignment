package com.revanth.swipe.navigation.di

import com.revanth.swipe.core.data.di.DataModule
import com.revanth.swipe.core.database.di.RoomModule
import com.revanth.swipe.core.network.NetworkModule
import com.revanth.swipe.feature.home.HomeModule
import com.revanth.swipe.feature.onboarding.OnboardingModule
import com.revanth.swipe.feature.settings.theme.ThemeModule
import com.revanth.swipe.navigation.RootModule
import org.koin.dsl.module

object KoinModules {

    val CoreModules = module{
        includes(
            NetworkModule,
            DataModule,
            RoomModule
        )
    }

    val FeatureModules= module {
        includes(
            HomeModule,
            OnboardingModule,
            ThemeModule
        )
    }

    val allModules = listOf(
        RootModule,
        CoreModules,
        FeatureModules
    )
}