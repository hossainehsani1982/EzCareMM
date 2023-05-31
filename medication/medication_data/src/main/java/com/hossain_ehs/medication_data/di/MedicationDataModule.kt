package com.hossain_ehs.medication_data.di

import android.content.Context
import com.hossain_ehs.core.local.entity.ApplicationDataBase
import com.hossain_ehs.medication_data.alarm.AlamSchedulerImpl
import com.hossain_ehs.medication_data.repository.MedicationRepositoryImpl
import com.hossain_ehs.medication_domain.model.alarm.AlarmScheduler
import com.hossain_ehs.medication_domain.repository.MedicationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MedicationDataModule {

    @Provides
    @Singleton
    fun provideMedicationRepository(
        db : ApplicationDataBase
    ) : MedicationRepository {
        return MedicationRepositoryImpl(
           applicationDao = db.dao)
    }


    @Provides
    @Singleton
    fun provideAlarmScheduler (
        @ApplicationContext context: Context,
    ) : AlarmScheduler{
        return AlamSchedulerImpl(
            context = context)
    }

}