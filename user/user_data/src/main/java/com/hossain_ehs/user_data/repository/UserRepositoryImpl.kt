package com.hossain_ehs.user_data.repository

import com.hossain_ehs.core.local.ApplicationDao
import com.hossain_ehs.user_data.mapper.toUser
import com.hossain_ehs.user_data.mapper.toUserEntity
import com.hossain_ehs.user_domain.model.User
import com.hossain_ehs.user_domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(
    private val applicationDao: ApplicationDao
) : UserRepository {

    override suspend fun insertUser(user: User) {
        applicationDao.insertUser(user.toUserEntity())
    }

    override suspend fun deleteUser(user: User) {
        applicationDao.deleteUser(user.toUserEntity())
    }

    override fun getUsers(): Flow<List<User>> {
        return applicationDao.getAllUsers().map { list ->
            list.map {
                it.toUser() }
        }
    }

    override suspend fun getUserById(id: Int): User {
        return applicationDao.getUserById(id).toUser()
    }

}