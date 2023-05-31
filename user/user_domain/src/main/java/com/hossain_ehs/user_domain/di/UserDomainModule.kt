package com.hossain_ehs.user_domain.di

import com.hossain_ehs.user_domain.repository.UserRepository
import com.hossain_ehs.user_domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
object UserDomainModule {


    @Provides
    @ViewModelScoped
    fun provideUserUseCase(
        repository: UserRepository
    ) : UserUseCases{
        return UserUseCases(
            deleteUserUseCase = DeleteUserUseCase(repository),
            insertUserUseCase = InsertUserUseCase(repository),
            getAllUserUseCase = GetAllUsersUseCase(repository),
            getUserByIdUseCase = GetUserById(repository)
        )
    }
}