package com.revanth.swipe

import android.app.Application
import com.revanth.swipe.navigation.di.KoinModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SwipeApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SwipeApp)
            modules(KoinModules.allModules)
        }
    }
}