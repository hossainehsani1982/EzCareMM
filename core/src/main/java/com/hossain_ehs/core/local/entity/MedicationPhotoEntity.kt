package com.hossain_ehs.core.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medication_photo_table")
data class MedicationPhotoEntity (
    val patientName : String,
    val patientId : Int,
    val medicationName : String,
    val isTakenByCamera : Boolean,
    val medicationId : Int,
    var isDefault : Boolean,
    val dateTaken : Long,
    val medicationPhotoRelativePath : String,
    val medicationUri : String,
    @PrimaryKey(autoGenerate = true) val medicationPhotoId : Int = 0
        )