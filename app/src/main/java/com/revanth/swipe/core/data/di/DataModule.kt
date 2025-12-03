package com.revanth.swipe.core.data.di

import com.revanth.swipe.core.data.repos.ConnectivityRepository
import com.revanth.swipe.core.data.repos.ProductRepository
import com.revanth.swipe.core.data.reposImpl.ConnectivityRepositoryImpl
import com.revanth.swipe.core.data.reposImpl.ProductRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val DataModule = module {
    single<ProductRepository> { ProductRepositoryImpl(get()) }

    single<ConnectivityRepository> {
        ConnectivityRepositoryImpl(androidContext())
    }
}