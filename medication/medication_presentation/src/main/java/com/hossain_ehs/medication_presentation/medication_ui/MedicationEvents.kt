package com.hossain_ehs.medication_presentation.medication_ui

import com.hossain_ehs.medication_domain.model.medication.Medication

sealed class MedicationEvents{
    object OnAddMedicationClicked : MedicationEvents()
    data class OnDeleteMedicationClicked(val medication: Medication) : MedicationEvents()
    data class OnEditMedicationClicked (val medication: Medication) : MedicationEvents()
    data class OnResultReceived(val result: Int) : MedicationEvents()
    data class OnUndoDeleteMedicationClicked(val medication: Medication) : MedicationEvents()
    data class SearchMedicationByMedicineName(val query: String) : MedicationEvents()
    data class SearchMedicationByDoctorName(val query: String) : MedicationEvents()
    data class SearchMedicationByNote(val query: String) : MedicationEvents()

}
