package com.revanth.swipe.core.database.enitities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.revanth.swipe.core.models.ThemeConfig

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val firstTimeUser: Boolean = true,
    val theme: String= ThemeConfig.SYSTEM.themeName
)