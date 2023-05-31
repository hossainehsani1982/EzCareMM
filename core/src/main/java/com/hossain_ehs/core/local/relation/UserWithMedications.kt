package com.hossain_ehs.core.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.hossain_ehs.core.local.entity.MedicationEntity
import com.hossain_ehs.core.local.entity.UserEntity

data class UserWithMedications(
    @Embedded
    val user: UserEntity,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userId"
    )
    val medications: List<MedicationEntity>
)