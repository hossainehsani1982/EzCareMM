package com.hossain_ehs.core.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "schedule_table")
data class ScheduleEntity (
    val scheduleForPerson : String,
    val scheduleForMedication : String,
    val approximateUsagePeriod : String,
    val medicationId : Int,
    val userId : Int,
    var nextSchedule: Long,
    val hasTaken: Boolean = false,
    @PrimaryKey(autoGenerate = true) val scheduleId: Int = 0
)