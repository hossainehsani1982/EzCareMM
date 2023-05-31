package com.hossain_ehs.medication_data.mapper

import com.hossain_ehs.core.local.entity.ArchivedScheduleEntity
import com.hossain_ehs.medication_domain.model.schedule.ArchivedSchedule

fun ArchivedSchedule.toArchivedScheduleEntity(): ArchivedScheduleEntity {
    return ArchivedScheduleEntity(
        scheduleForPerson = scheduleForPerson,
        scheduleForMedication = scheduleForMedication,
        approximateUsagePeriod = approximateUsagePeriod,
        medicationId = medicationId,
        userId = userId,
        scheduledTime = scheduledTime,
        hasTaken = hasTaken,
        archivedScheduleId = archivedScheduleId
    )
}

fun ArchivedScheduleEntity.toArchivedSchedule(): ArchivedSchedule {
    return ArchivedSchedule(
        scheduleForPerson = scheduleForPerson,
        scheduleForMedication = scheduleForMedication,
        approximateUsagePeriod = approximateUsagePeriod,
        medicationId = medicationId,
        userId = userId,
        scheduledTime = scheduledTime,
        hasTaken = hasTaken,
        archivedScheduleId = archivedScheduleId
    )
}