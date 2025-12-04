package com.revanth.swipe.core.data.repos

import com.revanth.swipe.core.database.enitities.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUser(): Flow<UserEntity?>
    suspend fun setFirstTimeUser(isFirstTime: Boolean)
    suspend fun updateTheme(theme: String)
}
