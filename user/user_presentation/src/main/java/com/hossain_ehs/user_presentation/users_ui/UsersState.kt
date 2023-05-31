package com.hossain_ehs.user_presentation.users_ui

import com.hossain_ehs.user_domain.model.User
import kotlinx.coroutines.flow.Flow

data class UsersState(
    var users: Flow<List<User>>? = null,
    var deletedUser: User? = null
)
