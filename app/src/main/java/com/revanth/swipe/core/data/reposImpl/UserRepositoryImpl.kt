package com.revanth.swipe.core.data.reposImpl

import com.revanth.swipe.core.data.repos.UserRepository
import com.revanth.swipe.core.database.dao.UserDao
import com.revanth.swipe.core.database.enitities.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {

    override fun getUser(): Flow<UserEntity?> = userDao.getUser()

    override suspend fun setFirstTimeUser(isFirstTime: Boolean) {
        val user = userDao.getUser().first()

        if (user == null) {
            userDao.insertUser(UserEntity(firstTimeUser = isFirstTime))
        } else {
            userDao.updateFirstTimeUser(user.id, isFirstTime)
        }
    }
}
