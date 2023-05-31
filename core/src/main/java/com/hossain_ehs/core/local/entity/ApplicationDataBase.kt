package com.hossain_ehs.core.local.entity

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hossain_ehs.core.local.ApplicationDao

@Database(
    entities = [
        UserEntity::class,
        MedicationEntity::class,
        LaboratoryEntity::class,
        MedicationPhotoEntity::class,
        ScheduleEntity::class,
        ProgressPhotoEntity::class,
        MedicineDataEntity::class,
        ArchivedScheduleEntity::class
    ],
    version = 5
)
abstract class ApplicationDataBase() : RoomDatabase() {

    abstract val dao: ApplicationDao
}