package com.hossain_ehs.user_domain.use_case

import com.hossain_ehs.user_domain.model.User
import com.hossain_ehs.user_domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetAllUsersUseCase(
    private val repository: UserRepository
) {
     operator fun invoke() : Flow<List<User>> {
         return repository.getUsers()
     }
}