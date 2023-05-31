package com.hossain_ehs.user_presentation.add_edit_user_ui

import com.hossain_ehs.user_domain.model.HelpType

sealed class AddEditUserEvents {
    object OnSaveUserClicked : AddEditUserEvents()
    object OnUpdateUserClicked : AddEditUserEvents()
    data class OnHelpTypeChanged(val helpType: HelpType) : AddEditUserEvents()

    data class OnUserImageUriChanged(val userImageUri: String) : AddEditUserEvents()
    data class OnUserFirstNameChanged(val userFirstName: String) : AddEditUserEvents()
    data class OnUserLastNameChanged(val userLastName: String) : AddEditUserEvents()
    data class OnUserAgeChanged(val userAge: Int) : AddEditUserEvents()
    data class OnUserTelChanged(val userTel: Long) : AddEditUserEvents()
    data class OnUserAddressChanged(val userAddress: String) : AddEditUserEvents()
    data class OnUserEmergencyNumberChanged(val userEmergencyNumber: Long) : AddEditUserEvents()


}