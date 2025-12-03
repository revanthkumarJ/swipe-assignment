package com.revanth.swipe.core.database.di

import androidx.room.Room
import com.revanth.swipe.core.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val RoomModule = module {

    // Provide Room DB
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "swipe_database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    single { get<AppDatabase>().productDao() }
    single { get<AppDatabase>().unsyncedProductDao() }
    single { get<AppDatabase>().userDao() }
}
