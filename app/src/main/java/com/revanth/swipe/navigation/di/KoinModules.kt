package com.revanth.swipe.navigation.di

import com.revanth.swipe.core.data.di.DataModule
import com.revanth.swipe.core.network.NetworkModule
import com.revanth.swipe.navigation.RootModule

object KoinModules {

    val allModules = listOf(
        RootModule,
        NetworkModule,
        DataModule
    )
}