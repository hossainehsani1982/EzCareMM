package com.hossain_ehs.user_data.mapper

import com.hossain_ehs.core.local.entity.UserEntity
import com.hossain_ehs.user_domain.model.HelpType
import com.hossain_ehs.user_domain.model.User

fun User.toUserEntity() : UserEntity {
    return UserEntity(
        fullName = fullName,
        age = age,
        tel = tel,
        address = address,
        userImageUri = userImageUri,
        emergencyNumber = emergencyNumber,
        helpType = helpType.type,
        userId = userId
    )
}

fun UserEntity.toUser() : User {
    return User(
        fullName = fullName,
        age = age,
        tel = tel,
        address = address,
        userImageUri = userImageUri,
        emergencyNumber = emergencyNumber,
        helpType = HelpType.fromString(helpType),
        userId = userId
    )
}