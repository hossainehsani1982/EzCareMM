package com.hossain_ehs.medication_data.repository

import com.hossain_ehs.core.local.ApplicationDao
import com.hossain_ehs.medication_data.mapper.*
import com.hossain_ehs.medication_domain.model.schedule.Schedule
import com.hossain_ehs.medication_domain.model.medication.Medication
import com.hossain_ehs.medication_domain.model.schedule.ArchivedSchedule
import com.hossain_ehs.medication_domain.repository.MedicationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MedicationRepositoryImpl(
    private val applicationDao: ApplicationDao,
) : MedicationRepository {

    override fun getMedicationsByUser(userId: Int): Flow<List<Medication>> {
        return applicationDao.getUserWithMedications(userId).map {
            it.first().medications.map { medicationEntity ->
                medicationEntity.toMedication()
            }
        }
    }

    override fun getMedicationsByMedicationName(
        medicationName: String,
        userId: Int
    ): Flow<List<Medication>> {
        return applicationDao.getLiveMedicationSearchByMedicationResult(
            medicationName,
            userId
        ).map {
            it.map { medicationEntity ->
                medicationEntity.toMedication()
            }
        }
    }

    override fun getMedicationsByDoctorName(
        doctorName: String,
        userId: Int
    ): Flow<List<Medication>> {
        return applicationDao.getLiveMedicationSearchByDoctorNameResult(
            doctorName,
            userId
        ).map {
            it.map { medicationEntity ->
                medicationEntity.toMedication()
            }
        }
    }

    override fun getMedicationsByNoteQuery(
        noteQuery: String,
        userId: Int
    ): Flow<List<Medication>> {
        return applicationDao.getLiveMedicationSearchByShortDescriptionResult(
            noteQuery,
            userId
        ).map {
            it.map { medicationEntity ->
                medicationEntity.toMedication()
            }
        }
    }

    /**
     * will be used to get medication list to get newly added medication to obtain its id
     * */
    override suspend fun getAllMedicationsDESC(): List<Medication> {
        return applicationDao.getAllMedicationsDESC().map {
            it.toMedication()
        }
    }


    override fun getUserName(userId: Int): Flow<String> {
        return applicationDao.getUserWithMedications(userId).map {
            it.first().user.fullName
        }
    }

    override fun getUserImageUri(userId: Int): String {
        return applicationDao.getUserWithMedications(userId).map {
            it.first().user.userImageUri
        }.toString()
    }


    override suspend fun insertMedication(medication: Medication) {
        applicationDao.insertMedication(medication.toMedicationEntity())
    }

    override suspend fun deleteMedication(medication: Medication) {
        applicationDao.deleteMedication(medication.toMedicationEntity())
    }

    override suspend fun getMedicationById(medicationId: Int): Medication {
        return applicationDao.getMedicationById(medicationId).toMedication()
    }

    override suspend fun getScheduleByMedicationId(medicationId: Int): List<Schedule> {
        val result = applicationDao.getSchedulesWithMedication(medicationId).first().schedules.map {
            it.toSchedule()
        }
        return result
    }

    override suspend fun insertSchedule(schedule: Schedule) {
         applicationDao.insertSchedule(schedule.toScheduleEntity())
    }

    override suspend fun deleteSchedule(schedule: Schedule) {
        applicationDao.deleteSchedule(
            schedule.toScheduleEntity()
        )
    }

    /***
     * will be used in service and alarm
     */

    override suspend fun getAllMedicationsOrderByScheduleACS(): List<Medication> {
        return applicationDao.getAllMedicationsOrderByScheduleACS().map {
            it.toMedication()
        }
    }

    /***
     * will be used in alarm
     */
    override suspend fun insertArchivedSchedule(archivedSchedule: ArchivedSchedule) {
        applicationDao.insertArchivedSchedule(archivedSchedule.toArchivedScheduleEntity())
    }

}