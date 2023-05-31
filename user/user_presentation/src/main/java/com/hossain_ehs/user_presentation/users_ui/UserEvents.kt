package com.hossain_ehs.user_presentation.users_ui

import com.hossain_ehs.user_domain.model.User

sealed class UserEvents{
    object OnAddUserClicked : UserEvents()
    data class OnDeleteUserClicked(val user: User) : UserEvents()
    data class OnEditUserClicked (val user: User) : UserEvents()
    data class OnToMedicationClicked(val user: User) : UserEvents()
    data class OnResultReceived(val result: Int) : UserEvents()
    data class OnUndoDeleteUserClicked(val user: User) : UserEvents()
}