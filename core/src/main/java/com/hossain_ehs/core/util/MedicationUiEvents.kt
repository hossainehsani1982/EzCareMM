package com.hossain_ehs.core.util

sealed class MedicationUiEvents{
    object NavigateToAddNewMedication: MedicationUiEvents()
    data class NavigateToEditMedication(val id : Int): MedicationUiEvents()
    data class ShowMessage (val message : UiText): MedicationUiEvents()
    object ShowUndoDeleteMedicationMessage: MedicationUiEvents()
}
