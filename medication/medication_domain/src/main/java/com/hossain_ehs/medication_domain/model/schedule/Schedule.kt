package com.hossain_ehs.medication_domain.model.schedule

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Schedule(
    val scheduleId: Int = 0,
    val scheduleForPerson : String,
    val scheduleForMedication : String,
    val approximateUsagePeriod : String,
    val medicationId : Int,
    val userId : Int,
    var nextSchedule: Long,
    val hasTaken: Boolean = false,
) : Parcelable
