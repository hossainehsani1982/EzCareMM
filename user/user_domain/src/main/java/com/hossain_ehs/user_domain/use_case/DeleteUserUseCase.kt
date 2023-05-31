package com.hossain_ehs.user_domain.use_case

import com.hossain_ehs.user_domain.model.User
import com.hossain_ehs.user_domain.repository.UserRepository

class DeleteUserUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(
        user: User
    ) {
        repository.deleteUser(user)
    }
}