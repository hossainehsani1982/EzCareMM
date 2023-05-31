package com.hossain_ehs.medication_domain.di

import com.hossain_ehs.core.domain.shared_preferences.Preferences
import com.hossain_ehs.medication_domain.repository.MedicationRepository
import com.hossain_ehs.medication_domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MedicationDomainModule {


    @Provides
    @Singleton
    fun provideCalculateShamsiDaysUseCase() : CalculateShamsiDaysUseCase{
        return CalculateShamsiDaysUseCase()
    }
    @Provides
    @Singleton
    fun provideCalculateGregorianDaysUseCase() : CalculateGregorianDaysUseCase{
        return CalculateGregorianDaysUseCase()
    }
    @Provides
    @Singleton
    fun provideEnglishNumberFormatterUseCase() : EnglishNumberFormatterUseCase{
        return EnglishNumberFormatterUseCase()
    }
    @Provides
    @Singleton
    fun provideEnglishToPersianFormatterUseCase() : EnglishToPersianFormatterUseCase{
        return EnglishToPersianFormatterUseCase()
    }




    @Provides
    @Singleton
    fun provideMedicationUseCase(
        repository: MedicationRepository,
        englishToPersianFormatter: EnglishToPersianFormatterUseCase,
        englishNumberFormatter: EnglishNumberFormatterUseCase,
        calculateShamsiDays: CalculateShamsiDaysUseCase,
        calculateGregorianDays: CalculateGregorianDaysUseCase,
        preferences: Preferences,
    ) : MedicationUseCases{
        return MedicationUseCases(
            deleteMedicationUseCase = DeleteMedicationUseCase(repository),
            insertMedicationUseCase = InsertMedicationUseCase(repository),
            getAllMedicationUseCase = GetMedicationsByUserUseCase(repository),
            getMedicationByIdUseCase = GetMedicationByIdUseCase(repository),
            getUserNameUseCase = GetUserNameUseCase(repository),
            getUserImageUriUseCase = GetUserImageUriUseCase(repository),
            calculateStartScheduleUseCase = CalculateStartScheduleUseCase(),
            calculateNextScheduleUseCase = CalculateNextScheduleUseCase(
                gregorianDaysUseCase = calculateGregorianDays),
            englishToPersianFormatterUseCase = EnglishToPersianFormatterUseCase(),
            englishNumberFormatterUseCase = EnglishNumberFormatterUseCase(),
            calculateShamsiDaysUseCase = CalculateShamsiDaysUseCase(),
            calculateGregorianDays = CalculateGregorianDaysUseCase(),
            getDaysUseCase = GetDaysUseCase(
                calculateShamsiDays = calculateShamsiDays,
                calculateGregorianDays = calculateGregorianDays,
                englishNumberFormatter = englishNumberFormatter,
                englishToPersianFormatter = englishToPersianFormatter
            ),
            getHoursUseCase = GetHoursUseCase(
                englishNumberFormatter = englishNumberFormatter,
                englishToPersianFormatter = englishToPersianFormatter
            ),
            getMinutesUseCase = GetMinutesUseCase(
                englishNumberFormatter = englishNumberFormatter,
                englishToPersianFormatter = englishToPersianFormatter
            ),
            getYearsUseCase = GetYearsUseCase(
                englishToPersianFormatter = englishToPersianFormatter
            ),
            dateToStringUseCase = DateToStringUseCase(
                englishToPersianFormatter = englishToPersianFormatter,
                englishNumberFormatter = englishNumberFormatter
            ),
            setIntervalValueUseCase = SetIntervalValueUseCase(),

            getDateFromDbUseCase = GetDateFromDbUseCase(
                englishToPersianFormatter = englishToPersianFormatter,
            ),
            setYearUseCase = SetYearUseCase(
                englishToPersianFormatter = englishToPersianFormatter,
            ),
            setMonthUseCase = SetMonthUseCase(
                englishToPersianFormatter = englishToPersianFormatter,
            ),
            setDayUseCase = SetDayUseCase(
                englishToPersianFormatter = englishToPersianFormatter,
            ),
            setHourUseCase = SetHourUseCase(
                englishToPersianFormatter = englishToPersianFormatter,
            ),
            setMinuteUseCase = SetMinuteUseCase(
                englishToPersianFormatter = englishToPersianFormatter,
            ),
            getDaysByMonthAndYearUseCase = GetDaysByMonthAndYearUseCase(
                calculateShamsiDays = calculateShamsiDays,
                calculateGregorianDays = calculateGregorianDays,
                englishNumberFormatter = englishNumberFormatter,
                englishToPersianFormatter = englishToPersianFormatter
            ),
            searchMedicationsByNameUseCase = SearchMedicationsByNameUseCase(
                repository
            ),
            searchMedicationsByDoctorNameUseCase = SearchMedicationsByDoctorNameUseCase(
                repository
            ),
            searchMedicationsByNoteUseCase = SearchMedicationsByNoteUseCase(
                repository
            ),
            getScheduleByMedicationIdUseCase = GetScheduleByMedicationIdUseCase(
                repository
            ),
            insertScheduleUseCase = InsertScheduleUseCase(
                repository = repository
            ),
            deleteScheduleUseCase = DeleteScheduleUseCase(
                repository
            ),
            getAllMedicationsDESCUseCase = GetAllMedicationsDESCUseCase(
                repository
            ),
            getNumberOfInsertIterationsUseCase = GetNumberOfInsertIterationsUseCase(),
            getServiceStatusUseCase = GetServiceStatusUseCase(
                preferences = preferences
            ),
            setServiceStatusUseCase = SetServiceStatusUseCase(
                preferences = preferences
            ),
            dateToStringForAlarmUseCase = DateToStringForAlarmUseCase(),
            getMedicationsOrdByScheduleACSUseCase = GetMedicationsOrdByScheduleACSUseCase(
                repository
            ),
            insertArchivedScheduleUseCase = InsertArchivedScheduleUseCase(
                repository
            ),
        )
    }

}