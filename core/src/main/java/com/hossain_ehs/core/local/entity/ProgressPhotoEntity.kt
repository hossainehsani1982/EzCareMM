package com.hossain_ehs.core.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "medication_progress_photo_table")
data class ProgressPhotoEntity(
    val relatedMedicineName : String,
    val medicationId : Int,
    val isTakenByCamera: Boolean,
    val userId : Int,
    val dateTaken : Long,
    val progressPhotoUri : String,
    @PrimaryKey(autoGenerate = true) val progressPhotoId: Int = 0
)
