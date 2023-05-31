package com.hossain_ehs.medication_on_alarm_ui

import com.hossain_ehs.medication_domain.model.medication.Medication

sealed class MedicationAlarmEvents{
    object OnMedicationTakenSwiped : MedicationAlarmEvents()
    object OnMedicationNotTakenSwiped : MedicationAlarmEvents()
    object OnSnoozeClicked : MedicationAlarmEvents()
    object OnDismissClicked : MedicationAlarmEvents()
    data class GetCorrespondingUserData(val medication: Medication) : MedicationAlarmEvents()

}
