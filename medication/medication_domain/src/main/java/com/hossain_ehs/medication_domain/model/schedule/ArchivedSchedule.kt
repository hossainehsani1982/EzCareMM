package com.hossain_ehs.medication_domain.model.schedule

data class ArchivedSchedule(
    val scheduleForPerson: String,
    val scheduleForMedication: String,
    val approximateUsagePeriod: String,
    val medicationId: Int,
    val userId: Int,
    var scheduledTime: Long,
    val hasTaken: Boolean = false,
    val archivedScheduleId: Int = 0
)
