package com.hossain_ehs.medication_presentation.add_edit_medication_ui

import androidx.lifecycle.SavedStateHandle
import com.hossain_ehs.core.util.Mode
import com.hossain_ehs.medication_domain.model.medication.Medication
import com.hossain_ehs.medication_domain.model.medication.MedicineType
import com.hossain_ehs.medication_domain.model.medication.MedicationUsageStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class AddEditMedicationStates(
    val savedStateHandle: SavedStateHandle
) {
    var userNameState = savedStateHandle.get<String>("userName") ?: "t"
       private set(value) {
            field = value
            savedStateHandle["userName"] = value
        }
    fun updateUserNameState(value: String) {
        userNameState = value
    }

    var yearsStates = savedStateHandle.get<Array<String>>("years") ?: arrayOf()
       private set(value) {
            field = value
            savedStateHandle["years"] = value
        }
    fun updateYearsStates(value: Array<String>) {
        yearsStates = value
    }

    var medicationToEditState = savedStateHandle.get<Medication>("medicationToEdit")
       private set(value) {
            field = value
            savedStateHandle["medicationToEdit"] = value
        }
    fun updateMedicationToEditState(value: Medication) {
        medicationToEditState = value
    }
    var modeState = savedStateHandle.get<Mode>("mode") ?: Mode.ADD
       private set(value) {
            field = value
            savedStateHandle["mode"] = value
        }
    fun updateModeState(value: Mode) {
        modeState = value
    }

    var isDataOkState = savedStateHandle.get<Boolean>("isDataOk") ?: false
         private set(value) {
                field = value
                savedStateHandle["isDataOk"] = value
          }
    fun updateIsDataOkState(value: Boolean) {
        isDataOkState = value
    }
//    var isDuplicateMedicationForaUserState = savedStateHandle
//        .get<Boolean>("isDuplicateMedicationForaUser") ?: false
//            private set(value) {
//                    field = value
//                    savedStateHandle["isDuplicateMedicationForaUser"] = value
//            }
//    fun updateIsDuplicateMedicationForaUserState(value: Boolean) {
//            isDuplicateMedicationForaUserState = value
//    }
     var languageState = savedStateHandle.get<String>("language")
            private set(value) {
                    field = value
                    savedStateHandle["language"] = value
            }
    fun updateLanguageState(value: String) {
            languageState = value
    }

//    var englishToPersianValueState = savedStateHandle.get<String>("englishToPersianValue") ?: ""
//            private set(value) {
//                    field = value
//                    savedStateHandle["englishToPersianValue"] = value
//            }

    var daysInMonthMaxValState = savedStateHandle
        .get<Array<String>>("daysInMonthMaxVAl") ?: arrayOf()
            private set(value) {
                    field = value
                    savedStateHandle["daysInMonthMaxVAl"] = value
            }
    fun updateDaysInMonthMaxValState(value: Array<String>) {
            daysInMonthMaxValState = value
    }
    var startingScheduleToStringState = savedStateHandle
        .get<String>("startingScheduleToString") ?: ""
            private set(value) {
                    field = value
                    savedStateHandle["startingScheduleToString"] = value
            }
    fun updateStartingScheduleToStringState(value: String) {
            startingScheduleToStringState = value
    }
    var monthsState = savedStateHandle.get<Array<String>>("months") ?: arrayOf()
            private set(value) {
                    field = value
                    savedStateHandle["months"] = value
            }
    fun updateMonthsState(value: Array<String>) {
            monthsState = value
    }
    var nextScheduleToStringState = savedStateHandle.get<String>("nextScheduleToString") ?: ""
            private set(value) {
                    field = value
                    savedStateHandle["nextScheduleToString"] = value
            }
    fun updateNextScheduleToString(value: String) {
        nextScheduleToStringState = value
    }
    var startScheduleYearState = savedStateHandle.get<Int>("startScheduleYear") ?: 1975
            private set(value) {
                    field = value
                    savedStateHandle["startScheduleYear"] = value
            }
    fun updateStartScheduleYearState(value: Int) {
            startScheduleYearState = value
    }
    var startScheduleMonthState = savedStateHandle.get<Int>("startScheduleMonth") ?: 1
            private set(value) {
                    field = value
                    savedStateHandle["startScheduleMonth"] = value
            }
    fun updateStartScheduleMonthState(value: Int) {
            startScheduleMonthState = value
    }
    var startScheduleDayState = savedStateHandle.get<Int>("startScheduleDay") ?: 1
            private set(value) {
                    field = value
                    savedStateHandle["startScheduleDay"] = value
            }
    fun updateStartScheduleDayState(value: Int) {
            startScheduleDayState = value
    }
    var startScheduleHoursState = savedStateHandle
        .get<Array<String>>("startScheduleHour") ?: arrayOf()
            private set(value) {
                    field = value
                    savedStateHandle["startScheduleHour"] = value
            }
    fun updateStartScheduleHoursState(value: Array<String>) {
            startScheduleHoursState = value
    }
    var startScheduleHourOfDayState = savedStateHandle.get<Int>("startScheduleHourOfDay") ?: 1
            private set(value) {
                    field = value
                    savedStateHandle["startScheduleHourOfDay"] = value
            }
    fun updateStartScheduleHourOfDay(value: Int) {
        startScheduleHourOfDayState = value
    }

    var startScheduleMinutesState = savedStateHandle
        .get<Array<String>>("startScheduleMinute") ?: arrayOf()
            private set(value) {
                    field = value
                    savedStateHandle["startScheduleMinute"] = value
            }
    fun updateStartScheduleMinutesState(value: Array<String>) {
            startScheduleMinutesState = value
    }
    var startScheduleMinuteOfDay = savedStateHandle.get<Int>("startScheduleMinuteOfDay") ?: 1
            private set(value) {
                    field = value
                    savedStateHandle["startScheduleMinuteOfDay"] = value
            }
    fun updateStartScheduleMinuteOfDay(value: Int) {
        startScheduleMinuteOfDay = value
    }

    var doctorsDataResultState = savedStateHandle
        .get<Flow<List<String>>>("doctorsDataResult") ?: flowOf()
            private set(value) {
                    field = value
                    savedStateHandle["doctorsDataResult"] = value
            }
    fun updateDoctorsDataResultState(value: Flow<List<String>>) {
            doctorsDataResultState = value
    }
    var userIdState = savedStateHandle.get<Int>("userId") ?: 0
            private set(value) {
                    field = value
                    savedStateHandle["userId"] = value
            }
    fun updateUserIdState(value: Int) {
            userIdState = value
    }
    var laboratoryIdState = savedStateHandle.get<Int>("laboratoryId") ?: 0
            private set(value) {
                    field = value
                    savedStateHandle["laboratoryId"] = value
            }
    fun updateLaboratoryIdState(value: Int) {
            laboratoryIdState = value
    }
    var medicationIdState = savedStateHandle.get<Int>("medicationId")   ?: 0
            private set(value) {
                    field = value
                    savedStateHandle["medicationId"] = value
            }
    fun updateMedicationIdState(value: Int) {
            medicationIdState = value
    }
    var medicineNameState = savedStateHandle.get<String>("medicineName") ?: ""
            private set(value) {
                    field = value
                    savedStateHandle["medicineName"] = value
            }
    fun updateMedicineNameState(value: String) {
            medicineNameState = value
    }
    var doctorNameState = savedStateHandle.get<String>("doctorName")  ?: "t"
            private set(value) {
                    field = value
                    savedStateHandle["doctorName"] = value
            }
    fun updateDoctorNameState(value: String) {
            doctorNameState = value
    }
    var medicationImageUriState = savedStateHandle.get<String>("medicationImageUri") ?: ""
            private set(value) {
                    field = value
                    savedStateHandle["medicationImageUri"] = value
            }
    fun updateMedicationImageUriState(value: String) {
            medicationImageUriState = value
    }
    var medicationNotesState = savedStateHandle.get<String>("medicationNotes") ?: "t"
            private set(value) {
                    field = value
                    savedStateHandle["medicationNotes"] = value
            }
    fun updateMedicationNotesState(value: String) {
            medicationNotesState = value
    }
    var medicationDosageState = savedStateHandle.get<String>("medicationDosage") ?: "1"
            private set(value) {
                    field = value
                    savedStateHandle["medicationDosage"] = value
            }
    fun updateMedicationDosageState(value: String) {
            medicationDosageState = value
    }
    var startingScheduleState = savedStateHandle.get<Long>("startingSchedule") ?: 0
            private set(value) {
                    field = value
                    savedStateHandle["startingSchedule"] = value
            }
    fun updateStartingScheduleState(value: Long) {
            startingScheduleState = value
    }
    var nextScheduleState = savedStateHandle.get<Long>("nextSchedule") ?: 0
            private set(value) {
                    field = value
                    savedStateHandle["nextSchedule"] = value
            }
    fun updateNextScheduleState(value: Long) {
            nextScheduleState = value
    }
    var intervalUnitState = savedStateHandle.get<Int>("intervalUnit")   ?: 1
            private set(value) {
                    field = value
                    savedStateHandle["intervalUnit"] = value
            }
    fun updateIntervalUnitState(value: Int) {
            intervalUnitState = value
    }
    var intervalMaxValueState = savedStateHandle.get<Int>("intervalMaxValue") ?: 24
            private set(value) {
                    field = value
                    savedStateHandle["intervalMaxValue"] = value
            }
    fun updateIntervalMaxValueState(value: Int) {
            intervalMaxValueState = value
    }
    var intervalValueState = savedStateHandle.get<Int>("intervalValue") ?: 7
            private set(value) {
                    field = value
                    savedStateHandle["intervalValue"] = value
            }
    fun updateIntervalValueState(value: Int) {
            intervalValueState = value
    }
    var approximateUsagePeriodState = savedStateHandle.get<String>("approximateUsagePeriod") ?: "1"
            private set(value) {
                    field = value
                    savedStateHandle["approximateUsagePeriod"] = value
            }
    fun updateApproximateUsagePeriodState(value: String) {
            approximateUsagePeriodState = value
    }
    var medicationTypeState = savedStateHandle
        .get<MedicineType>("medicationType") ?: MedicineType.Tablet
            private set(value) {
                    field = value
                    savedStateHandle["medicationType"] = value
            }
    fun updateMedicationTypeState(value: MedicineType) {
            medicationTypeState = value
    }
    var medicationStatusState = savedStateHandle
        .get<MedicationUsageStatus>("medicationStatus") ?: MedicationUsageStatus.NotTaken
            private set(value) {
                    field = value
                    savedStateHandle["medicationStatus"] = value
            }
    fun updateMedicationStatusState(value: MedicationUsageStatus) {
            medicationStatusState = value
    }


}
