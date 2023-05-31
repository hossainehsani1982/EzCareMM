package com.hossain_ehs.core.local

import androidx.room.*
import com.example.ezcare.room_tables.relations.MedicationWithSchedules
import com.hossain_ehs.core.local.entity.ArchivedScheduleEntity
import com.hossain_ehs.core.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import com.hossain_ehs.core.local.entity.MedicationEntity
import com.hossain_ehs.core.local.entity.ScheduleEntity
import com.hossain_ehs.core.local.relation.UserWithMedications

@Dao
interface ApplicationDao {

    /**
     * User Dao
     * **/
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userEntity: UserEntity)

    @Transaction
    @Delete
    suspend fun deleteUser(userEntity: UserEntity)

    @Transaction
    @Query("SELECT * FROM user_table")
    fun getAllUsers(): Flow<List<UserEntity>>

    @Transaction
    @Query("SELECT * FROM user_table WHERE userId = :id")
    suspend fun getUserById(id: Int): UserEntity

    /**
     * Medication Dao
     * **/

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedication(medicationEntity: MedicationEntity)

    @Transaction
    @Delete
    suspend fun deleteMedication(medicationEntity: MedicationEntity)

    @Transaction
    @Query(
        """ 
        SELECT * FROM user_table
        WHERE userId = :userId"""
    )
    fun getUserWithMedications(userId: Int): Flow<List<UserWithMedications>>

    @Transaction
    @Query(
        """
        SELECT * FROM medication_table
        WHERE userId = :userId 
        AND medicineName LIKE '%' || :query || '%'
    """
    )
    fun getLiveMedicationSearchByMedicationResult(query: String, userId: Int):
            Flow<List<MedicationEntity>>

    @Transaction
    @Query(
        """
        SELECT * FROM medication_table
        WHERE userId = :userId
        AND doctorName LIKE '%' || :query || '%'
    """
    )
    fun getLiveMedicationSearchByDoctorNameResult(query: String, userId: Int):
            Flow<List<MedicationEntity>>

    @Transaction
    @Query(
        """
        SELECT * FROM medication_table 
        WHERE userId = :userId 
        AND shortDescription LIKE '%' || :query || '%'
    """
    )
    fun getLiveMedicationSearchByShortDescriptionResult(query: String, userId: Int):
            Flow<List<MedicationEntity>>

    @Transaction
    @Query("SELECT * FROM medication_table WHERE medicationId = :medicationId")
    suspend fun getMedicationById(medicationId: Int): MedicationEntity

    /**
     * on delete medication -> find and delete corresponding schedules
     **/

    @Transaction
    @Query("SELECT * FROM medication_table WHERE medicationId = :medicationId")
    suspend fun getSchedulesWithMedication(medicationId: Int): List<MedicationWithSchedules>

    @Transaction
    @Query("SELECT * FROM medication_table ORDER BY medicationId DESC")
    suspend fun getAllMedicationsDESC(): List<MedicationEntity>

    @Transaction
    @Query("SELECT * FROM medication_table ORDER BY nextSchedule ASC")
    suspend fun getAllMedicationsOrderByScheduleACS(): List<MedicationEntity>

    @Transaction
    @Query("SELECT * FROM schedule_table WHERE medicationId = :medicationId ORDER BY nextSchedule ASC")
    suspend fun getSchedulesWithMedicationId(medicationId: Int): List<ScheduleEntity>


    /**
     * medication Schedule
     **/

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchedule(schedule: ScheduleEntity)

    @Transaction
    @Delete
    suspend fun deleteSchedule(schedule: ScheduleEntity)

    @Transaction
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArchivedSchedule(archivedScheduleEntity: ArchivedScheduleEntity)

}