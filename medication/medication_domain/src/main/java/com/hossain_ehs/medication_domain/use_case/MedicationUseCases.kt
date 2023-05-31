package com.hossain_ehs.medication_domain.use_case

data class MedicationUseCases(
    val insertMedicationUseCase: InsertMedicationUseCase,
    val getAllMedicationUseCase: GetMedicationsByUserUseCase,
    val deleteMedicationUseCase: DeleteMedicationUseCase,
    val getMedicationByIdUseCase: GetMedicationByIdUseCase,
    val getUserNameUseCase: GetUserNameUseCase,
    val getUserImageUriUseCase: GetUserImageUriUseCase,
    val getAllMedicationsDESCUseCase: GetAllMedicationsDESCUseCase,
    val calculateStartScheduleUseCase: CalculateStartScheduleUseCase,
    val englishToPersianFormatterUseCase: EnglishToPersianFormatterUseCase,
    val englishNumberFormatterUseCase: EnglishNumberFormatterUseCase,
    val calculateShamsiDaysUseCase: CalculateShamsiDaysUseCase,
    val calculateGregorianDays: CalculateGregorianDaysUseCase,
    val calculateNextScheduleUseCase: CalculateNextScheduleUseCase,
    val getDaysUseCase: GetDaysUseCase,
    val getHoursUseCase: GetHoursUseCase,
    val getMinutesUseCase: GetMinutesUseCase,
    val getYearsUseCase: GetYearsUseCase,
    val dateToStringUseCase: DateToStringUseCase,
    val setIntervalValueUseCase: SetIntervalValueUseCase,
    val getDateFromDbUseCase: GetDateFromDbUseCase,
    val setYearUseCase: SetYearUseCase,
    val setMonthUseCase: SetMonthUseCase,
    val setDayUseCase: SetDayUseCase,
    val setHourUseCase: SetHourUseCase,
    val setMinuteUseCase: SetMinuteUseCase,
    val getDaysByMonthAndYearUseCase: GetDaysByMonthAndYearUseCase,
    val searchMedicationsByNameUseCase: SearchMedicationsByNameUseCase,
    val searchMedicationsByDoctorNameUseCase: SearchMedicationsByDoctorNameUseCase,
    val searchMedicationsByNoteUseCase: SearchMedicationsByNoteUseCase,

    val getScheduleByMedicationIdUseCase: GetScheduleByMedicationIdUseCase,
    val insertScheduleUseCase: InsertScheduleUseCase,
    val deleteScheduleUseCase: DeleteScheduleUseCase,

    val getNumberOfInsertIterationsUseCase: GetNumberOfInsertIterationsUseCase,

    val getServiceStatusUseCase: GetServiceStatusUseCase,
    val setServiceStatusUseCase: SetServiceStatusUseCase,
    val dateToStringForAlarmUseCase: DateToStringForAlarmUseCase,

    val getMedicationsOrdByScheduleACSUseCase: GetMedicationsOrdByScheduleACSUseCase,

    val insertArchivedScheduleUseCase: InsertArchivedScheduleUseCase


)
