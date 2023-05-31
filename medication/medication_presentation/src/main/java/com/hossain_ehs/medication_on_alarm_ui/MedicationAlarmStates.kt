package com.hossain_ehs.medication_on_alarm_ui

import androidx.lifecycle.SavedStateHandle
import com.hossain_ehs.medication_domain.model.medication.Medication
import com.hossain_ehs.medication_domain.model.schedule.Schedule

class MedicationAlarmStates (
    val savedStateHandle: SavedStateHandle
        ) {
    var medicationsState = savedStateHandle.get<List<Medication>>("medicationsState") ?: emptyList()
        private set(value) {
            field = value
            savedStateHandle["medicationsState"] = value
        }
    fun updateMedications(medication: List<Medication>) {
        this.medicationsState = medication
    }

    var userImageUriState = savedStateHandle.get<String>("userImageUriState") ?: ""
        private set(value) {
            field = value
            savedStateHandle["userImageUriState"] = value
        }
    fun updateUserImageUri(userImageUri: String) {
        this.userImageUriState = userImageUri
    }

    var medicationListSizeState = savedStateHandle.get<Int>("medicationListSize") ?: 1
        private set(value) {
            field = value
            savedStateHandle["medicationListSize"] = value
        }
    fun updateMedicationListSize(medicationListSize: Int) {
        this.medicationListSizeState = medicationListSize
    }

    var userNameState = savedStateHandle.get<String>("userNameState") ?: ""
        private set(value) {
            field = value
            savedStateHandle["userNameState"] = value
        }
    fun updateUserName(userName: String) {
        this.userNameState = userName
    }

    var scheduleToDelete = savedStateHandle.get<Schedule>("Schedule")
        private set(value) {
            field = value
            savedStateHandle["archivedSchedule"] = value
        }
    fun scheduleToDelete(archivedSchedule: Schedule) {
        this.scheduleToDelete = archivedSchedule
    }
}