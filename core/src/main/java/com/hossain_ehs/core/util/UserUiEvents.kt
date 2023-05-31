package com.hossain_ehs.core.util


sealed class UserUiEvents{
    object NavigateToAddNewUser: UserUiEvents()
    data class NavigateToEditUser(val id : Int): UserUiEvents()
    data class NavigateToMedication(val id : Int): UserUiEvents()
    data class ShowMessage (val message : UiText): UserUiEvents()
    object ShowUndoDeleteUserMessage: UserUiEvents()
}
