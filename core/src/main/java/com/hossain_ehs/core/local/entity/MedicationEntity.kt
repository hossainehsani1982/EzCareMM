package com.hossain_ehs.core.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medication_table")
data class MedicationEntity(
    val userName: String ,
    val userId: Int ,
    val laboratoryId : Int,
    val medicineName: String,
    val doctorName : String,
    val medicationImageUri : String,
    val shortDescription: String,
    val dosage: String ,
    val startingSchedule: Long ,
    val nextSchedule: Long ,
    val intervalUnit : Int ,
    val intervalValue : Int,
    val approximateUsagePeriod : String,
    val medicationType : String,
    val medicationStatus: String,
    @PrimaryKey(autoGenerate = true) val medicationId: Int = 0
    )
