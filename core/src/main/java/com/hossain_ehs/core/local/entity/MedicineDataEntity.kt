package com.hossain_ehs.core.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medication_data_table")
data class MedicineDataEntity (
    val drugName : String,
    val usageForm : String,
    val drugStrength : String,
    @PrimaryKey(autoGenerate = true) val medicationDataId : Int = 0
)