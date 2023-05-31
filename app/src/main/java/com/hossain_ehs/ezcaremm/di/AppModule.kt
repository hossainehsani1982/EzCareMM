package com.hossain_ehs.ezcaremm.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import com.hossain_ehs.core.data.shared_preferences.DefaultPreferences
import com.hossain_ehs.core.data.shared_preferences.PreferencesDataStore
import com.hossain_ehs.core.domain.shared_preferences.Preferences
import com.hossain_ehs.core.local.entity.ApplicationDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplicationDataBase(
        app: Application
    ): ApplicationDataBase {
        return Room.databaseBuilder(
            app,
            ApplicationDataBase::class.java,
            "user_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }


    @Provides
    @Singleton
    fun providesPreferencesDataStore(
        app: Application
    ) : PreferencesDataStore {
        return PreferencesDataStore(app.applicationContext)
    }

    @Provides
    @Singleton
    fun providesPreferences(preferencesDataStore: PreferencesDataStore) : Preferences {
        return DefaultPreferences(preferencesDataStore)
    }
}
