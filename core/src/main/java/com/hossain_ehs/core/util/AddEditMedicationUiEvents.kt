package com.hossain_ehs.core.util

sealed class AddEditMedicationUiEvents {
        data class NavigateToAddMedicationPhoto(val id : Int): AddEditMedicationUiEvents()
        data class ShowError(val message : UiText): AddEditMedicationUiEvents()
        object OnStartDateSet : AddEditMedicationUiEvents()
        data class NavigateWithResult(val result: Int) : AddEditMedicationUiEvents()
        object GetMonths : AddEditMedicationUiEvents()
        object LoadMedicationInfo : AddEditMedicationUiEvents()
}