package com.hossain_ehs.user_data.di


import com.hossain_ehs.core.local.entity.ApplicationDataBase
import com.hossain_ehs.user_data.repository.UserRepositoryImpl
import com.hossain_ehs.user_domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object UserDataModule {



    @Provides
    @ViewModelScoped
    fun provideUserRepository(
        db: ApplicationDataBase
    ): UserRepository {
        return UserRepositoryImpl(db.dao)
    }

}