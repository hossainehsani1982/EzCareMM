package com.hossain_ehs.user_presentation.add_edit_user_ui

import com.hossain_ehs.core.util.Mode
import com.hossain_ehs.user_domain.model.HelpType
import com.hossain_ehs.user_domain.model.User

data class AddEditUserState(
    val userToEdit : User? = null,
    val mode : Mode = Mode.ADD,
    val isDataOk : Boolean = false,
    val userId : Int = 0,
    val userFullName : String = "",
    val userFirstName : String = "",
    val userLastName : String = "",
    val userAge : Int = 0,
    val userTel : Long = 0L,
    val userAddress : String = "",
    val userImageUri : String = "",
    val userEmergencyNumber : Long = 911,
    val userHelpType : HelpType = HelpType.SelfHelper,
)


