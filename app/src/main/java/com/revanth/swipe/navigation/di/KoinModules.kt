package com.revanth.swipe.navigation.di

import com.revanth.swipe.core.data.di.DataModule
import com.revanth.swipe.core.network.NetworkModule
import com.revanth.swipe.feature.home.HomeModule
import com.revanth.swipe.navigation.RootModule
import org.koin.dsl.module

object KoinModules {

    val FeatureModules= module {
        includes(HomeModule)
    }

    val CoreModules = module{
        includes(
            NetworkModule,
            DataModule
        )
    }

    val allModules = listOf(
        RootModule,
        CoreModules,
        FeatureModules
    )
}