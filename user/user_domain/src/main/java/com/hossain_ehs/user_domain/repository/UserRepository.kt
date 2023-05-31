package com.hossain_ehs.user_domain.repository

import com.hossain_ehs.user_domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun insertUser(user: User)

    suspend fun deleteUser(user: User)

    fun getUsers(): Flow<List<User>>

    suspend fun getUserById(id: Int): User

}