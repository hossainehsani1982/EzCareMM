package com.hossain_ehs.core.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_table")
data class UserEntity(
    val fullName: String,
    val age: Int = 0,
    val tel: Long = 0,
    val address: String= "",
    val userImageUri : String = "",
    val emergencyNumber: Long = 0,
    val helpType: String = "self_helper",
    @PrimaryKey(autoGenerate = true) val userId: Int? = null
)