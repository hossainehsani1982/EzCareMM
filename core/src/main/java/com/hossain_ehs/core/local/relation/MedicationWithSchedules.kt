package com.example.ezcare.room_tables.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.hossain_ehs.core.local.entity.MedicationEntity
import com.hossain_ehs.core.local.entity.ScheduleEntity



data class MedicationWithSchedules (
    @Embedded
    val medication : MedicationEntity,

    @Relation(
        parentColumn = "medicationId",
        entityColumn = "medicationId"
    )
    val schedules : List<ScheduleEntity>
        )