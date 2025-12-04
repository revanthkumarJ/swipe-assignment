package com.revanth.swipe.core.common

import com.revanth.swipe.core.common.utils.NotificationHelper
import com.revanth.swipe.core.common.utils.NotificationRepository
import com.revanth.swipe.core.common.utils.NotificationRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val CommonModule = module {

    single { NotificationHelper(androidContext()) }

    single<NotificationRepository> { NotificationRepositoryImpl(get()) }
}
