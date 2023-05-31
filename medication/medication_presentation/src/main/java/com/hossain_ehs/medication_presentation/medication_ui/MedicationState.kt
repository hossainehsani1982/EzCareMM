package com.hossain_ehs.medication_presentation.medication_ui

import androidx.lifecycle.*
import com.hossain_ehs.medication_domain.model.medication.Medication
import com.hossain_ehs.medication_domain.model.schedule.Schedule
import kotlinx.coroutines.flow.Flow

class MedicationState(
    private val savedStateHandle: SavedStateHandle
) {

    var medicationSearchQueryState = savedStateHandle
        .getLiveData("medicationSearchQueryByMedicineName", "")
        private set(value) {
            field = value
            savedStateHandle["medicationSearchQueryByMedicineName"] = value
        }
    fun updateMedicationSearchQuery(medicationSearchQueryByMedicineName: String) {
        this.medicationSearchQueryState.value = medicationSearchQueryByMedicineName
    }

    var medicationsState: LiveData<List<Medication>>? = null
    fun updateMedications(medication: Flow<List<Medication>>) {
        this.medicationsState = medication.asLiveData()
    }

    var deletedMedicationState = savedStateHandle.get<Medication>("deletedMedicationState")
        private set(value) {
            field = value
            savedStateHandle["deletedMedicationState"] = value
        }
    fun updateDeletedMedication(deletedMedication: Medication?) {
        deletedMedicationState = deletedMedication
    }
    var deletedScheduleListState = savedStateHandle
        .get<List<Schedule>>("deletedScheduleListState") ?: emptyList()
        private set(value) {
            field = value
            savedStateHandle["deletedScheduleListState"] = value
        }
    fun updateDeletedScheduleList(deletedScheduleList: List<Schedule>?) {
        deletedScheduleListState = deletedScheduleList!!
    }

    var userNameState = savedStateHandle.get<String>("userNameState") ?: ""
        private set(value) {
            field = value
            savedStateHandle["userNameState"] = value
        }

    fun updateUserName(userName: String) {
        this.userNameState = userName
    }

    var userImageUriState = savedStateHandle.get<String>("userImageUriState") ?: ""
        private set(value) {
            field = value
            savedStateHandle["userImageUriState"] = value
        }
}
