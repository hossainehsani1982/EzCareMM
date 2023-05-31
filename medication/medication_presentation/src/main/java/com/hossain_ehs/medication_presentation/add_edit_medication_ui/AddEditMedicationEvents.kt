package com.hossain_ehs.medication_presentation.add_edit_medication_ui

import com.hossain_ehs.medication_domain.model.medication.Medication
import com.hossain_ehs.medication_domain.model.medication.MedicineType
import com.hossain_ehs.medication_domain.model.medication.MedicationUsageStatus
import com.hossain_ehs.medication_domain.model.medication.DateParts

sealed class AddEditMedicationEvents{
    object AddMedication
        : AddEditMedicationEvents()

    data class UpdateMedication(val medication: Medication)
        : AddEditMedicationEvents()

    data class OnMedicationNameChanged(val medicationName: String)
        : AddEditMedicationEvents()

    data class OnDoctorNameChanged(val doctorName: String) : AddEditMedicationEvents()

    data class OnMedicationNotesChanged(val medicationNotes: String)
        : AddEditMedicationEvents()

    data class OnMedicationDosageChanged(val medicationDosage: String)
        : AddEditMedicationEvents()

    data class OnMedicationImageUriChanged(val medicationImageUri: String)
        : AddEditMedicationEvents()

    data class OnMedicationStartingScheduleChanged(val startingSchedule: Long)
        : AddEditMedicationEvents()

    data class SetMonths(val months: Array<String>) : AddEditMedicationEvents() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as SetMonths

            if (!months.contentEquals(other.months)) return false

            return true
        }

        override fun hashCode(): Int {
            return months.contentHashCode()
        }
    }
    object SetStartingScheduleToString : AddEditMedicationEvents()

    data class OnMedicationNextScheduleChanged(val nextSchedule: Long)
        : AddEditMedicationEvents()

    data class OnMedicationIntervalUnitChanged(val intervalUnit: Int)
        : AddEditMedicationEvents()

    data class OnMedicationIntervalValueChanged(val intervalValue: Int)
        : AddEditMedicationEvents()

    data class OnMedicationApproximateUsagePeriodChanged(val approximateUsagePeriod: String)
        : AddEditMedicationEvents()

    data class OnMedicationTypeChanged(val medicationType: MedicineType)
        : AddEditMedicationEvents()

    data class OnMedicationStatusChanged(val medicationStatus: MedicationUsageStatus)
        : AddEditMedicationEvents()

    object OnButtonDoneClicked : AddEditMedicationEvents()

    data class SetYearPickerValue(val year : Int) : AddEditMedicationEvents()
    object SetInitialYearPickerValue : AddEditMedicationEvents()

    data class SetMonthPickerValue(val month : Int) : AddEditMedicationEvents()
    object SetInitialMonthPickerValue : AddEditMedicationEvents()

    data class SetDayPickerValue(val day : Int) : AddEditMedicationEvents()
    object SetDaysInMonthPickerValue : AddEditMedicationEvents()
    object SetInitialDayInMonthPickerValue : AddEditMedicationEvents()

    data class SetHourPickerValue(val hour : Int) : AddEditMedicationEvents()
    object SetHours : AddEditMedicationEvents()
    object SetInitialHourPickerValue : AddEditMedicationEvents()

    data class SetMinutePickerValue(val minute : Int) : AddEditMedicationEvents()
    object SetMinutes : AddEditMedicationEvents()
    object SetInitialMinutePickerValue : AddEditMedicationEvents()

    data class GetDatePart(val part : DateParts) : AddEditMedicationEvents()


}


