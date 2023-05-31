package com.hossain_ehs.core.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity("archived_table")
@Parcelize
data class ArchivedScheduleEntity(
    val scheduleForPerson: String,
    val scheduleForMedication: String,
    val approximateUsagePeriod: String,
    val medicationId: Int,
    val userId: Int,
    var scheduledTime: Long,
    val hasTaken: Boolean = false,
    @PrimaryKey(autoGenerate = true) val archivedScheduleId: Int = 0
) : Parcelable
