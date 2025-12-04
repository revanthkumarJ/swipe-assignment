package com.revanth.swipe.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.revanth.swipe.core.database.enitities.ProductEntity
import com.revanth.swipe.core.database.enitities.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users LIMIT 1")
    fun getUser(): Flow<UserEntity?>

    @Query("UPDATE users SET firstTimeUser = :isFirstTime WHERE id = :id")
    suspend fun updateFirstTimeUser(id: Int, isFirstTime: Boolean)

    @Query("UPDATE users SET theme = :theme WHERE id = :id")
    suspend fun updateTheme(id: Int, theme: String)
}