package com.hossain_ehs.user_domain.use_case

import com.hossain_ehs.user_domain.model.HelpType
import com.hossain_ehs.user_domain.model.User
import com.hossain_ehs.user_domain.repository.UserRepository

class InsertUserUseCase (
    private val repository: UserRepository
        ){
    suspend operator fun invoke(
        user: User
    ) = repository.insertUser(
        User(
            userId = user.userId,
            fullName = user.fullName,
            age = user.age,
            tel = user.tel,
            address = user.address,
            userImageUri = user.userImageUri,
            emergencyNumber = user.emergencyNumber,
            helpType = user.helpType
        )
    )
}