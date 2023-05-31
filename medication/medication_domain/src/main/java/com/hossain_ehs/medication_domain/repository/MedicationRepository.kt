package com.hossain_ehs.medication_domain.repository

import com.hossain_ehs.medication_domain.model.schedule.Schedule
import com.hossain_ehs.medication_domain.model.medication.Medication
import com.hossain_ehs.medication_domain.model.schedule.ArchivedSchedule
import kotlinx.coroutines.flow.Flow

interface MedicationRepository {
    fun getMedicationsByUser(userId : Int): Flow<List<Medication>>
    fun getMedicationsByMedicationName(medicationName : String, userId : Int): Flow<List<Medication>>
    fun getMedicationsByDoctorName(doctorName : String, userId : Int): Flow<List<Medication>>
    fun getMedicationsByNoteQuery(noteQuery : String, userId : Int): Flow<List<Medication>>
    suspend fun getAllMedicationsDESC(): List<Medication>
    fun getUserName (userId : Int): Flow<String>
    fun getUserImageUri (userId : Int): String
    suspend fun insertMedication(medication: Medication)
    suspend fun deleteMedication(medication: Medication)
    suspend fun getMedicationById(medicationId: Int): Medication

    suspend fun getScheduleByMedicationId(medicationId : Int): List<Schedule>
    suspend fun insertSchedule(schedule: Schedule)
    suspend fun deleteSchedule(schedule: Schedule)

    /***
     * will be used in service and alarm
     * **/
    suspend fun getAllMedicationsOrderByScheduleACS(): List<Medication>

    /***
     * will be used in alarm
     * **/
    suspend fun insertArchivedSchedule(archivedSchedule: ArchivedSchedule)
}