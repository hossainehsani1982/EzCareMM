package com.hossain_ehs.core.util

sealed class AddEditUserUiEvents {

    data class NavigateToAddUserPhoto(val id : Int): AddEditUserUiEvents()
    data class ShowError(val message : UiText): AddEditUserUiEvents()
    data class NavigateWithResult(val result: Int) : AddEditUserUiEvents()
    object LoadUserInfo : AddEditUserUiEvents()

}
