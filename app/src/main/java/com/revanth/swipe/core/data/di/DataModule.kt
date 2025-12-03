package com.revanth.swipe.core.data.di

import com.revanth.swipe.core.data.repos.ConnectivityRepository
import com.revanth.swipe.core.data.repos.ProductLocalRepository
import com.revanth.swipe.core.data.repos.ProductRepository
import com.revanth.swipe.core.data.repos.UserRepository
import com.revanth.swipe.core.data.reposImpl.ConnectivityRepositoryImpl
import com.revanth.swipe.core.data.reposImpl.ProductLocalRepositoryImpl
import com.revanth.swipe.core.data.reposImpl.ProductRepositoryImpl
import com.revanth.swipe.core.data.reposImpl.UserRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val DataModule = module {
    single<ProductRepository> { ProductRepositoryImpl(get()) }

    single<ConnectivityRepository> {
        ConnectivityRepositoryImpl(androidContext())
    }

    single<ProductLocalRepository> {
        ProductLocalRepositoryImpl(
            productDao = get(),
            unsyncedProductDao = get()
        )
    }

    single<UserRepository> {
        UserRepositoryImpl(
            userDao = get()
        )
    }
}