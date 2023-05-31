package com.hossain_ehs.core.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "laboratory_table")
data class LaboratoryEntity (
    val testSubjectName : String,
    val medicationId : Int,
    @PrimaryKey(autoGenerate = true) val laboratoryId : Int = 0
)