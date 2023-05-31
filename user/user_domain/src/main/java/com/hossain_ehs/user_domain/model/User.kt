package com.hossain_ehs.user_domain.model

data class User(
    val fullName: String,
    val age: Int = 0,
    val tel: Long = 0,
    val address: String= "",
    val userImageUri : String = "",
    val emergencyNumber: Long = 0,
    val helpType: HelpType,
    val userId: Int? = null
)
