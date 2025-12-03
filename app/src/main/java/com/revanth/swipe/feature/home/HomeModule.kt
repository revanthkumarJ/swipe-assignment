package com.revanth.swipe.feature.home

import com.revanth.swipe.navigation.RootNavViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module


val HomeModule = module {
    viewModelOf(::HomeViewModel)
}