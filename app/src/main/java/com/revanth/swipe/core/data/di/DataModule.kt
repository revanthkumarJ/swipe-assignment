package com.revanth.swipe.core.data.di

import com.revanth.swipe.core.data.repos.ProductRepository
import com.revanth.swipe.core.data.reposImpl.ProductRepositoryImpl
import org.koin.dsl.module

val DataModule = module {
    single<ProductRepository> { ProductRepositoryImpl(get()) }
}