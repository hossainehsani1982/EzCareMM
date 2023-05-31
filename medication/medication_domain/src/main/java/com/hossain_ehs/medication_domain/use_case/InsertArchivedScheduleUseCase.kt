package com.hossain_ehs.medication_domain.use_case

import com.hossain_ehs.medication_domain.model.schedule.ArchivedSchedule
import com.hossain_ehs.medication_domain.repository.MedicationRepository

class InsertArchivedScheduleUseCase(
    private val medicationRepository: MedicationRepository
) {
    suspend operator fun invoke(archivedSchedule: ArchivedSchedule) {
        medicationRepository.insertArchivedSchedule(archivedSchedule)
    }
}
