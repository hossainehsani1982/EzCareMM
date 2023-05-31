package com.hossain_ehs.medication_data.mapper

import com.hossain_ehs.core.local.entity.ScheduleEntity
import com.hossain_ehs.medication_domain.model.schedule.Schedule

fun Schedule.toScheduleEntity() : ScheduleEntity {
    return ScheduleEntity(
        scheduleId = scheduleId,
        scheduleForPerson = scheduleForPerson,
        scheduleForMedication = scheduleForMedication,
        approximateUsagePeriod = approximateUsagePeriod,
        medicationId = medicationId,
        userId = userId,
        nextSchedule = nextSchedule,
        hasTaken = hasTaken
    )
}

fun ScheduleEntity.toSchedule() : Schedule {
    return Schedule(
        scheduleId = scheduleId,
        scheduleForPerson = scheduleForPerson,
        scheduleForMedication = scheduleForMedication,
        approximateUsagePeriod = approximateUsagePeriod,
        medicationId = medicationId,
        userId = userId,
        nextSchedule = nextSchedule,
        hasTaken = hasTaken
    )
}