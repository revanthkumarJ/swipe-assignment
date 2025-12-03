package com.revanth.swipe.core.data.repos

import kotlinx.coroutines.flow.Flow

interface ConnectivityRepository {
    val isConnected: Flow<Boolean>
}