package com.hossain_ehs.user_domain.use_case

data class UserUseCases(
    val insertUserUseCase: InsertUserUseCase,
    val getAllUserUseCase: GetAllUsersUseCase,
    val deleteUserUseCase: DeleteUserUseCase,
    val getUserByIdUseCase: GetUserById
)