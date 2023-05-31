package com.hossain_ehs.medication_domain.use_case

import com.hossain_ehs.medication_domain.model.schedule.Schedule
import com.hossain_ehs.medication_domain.repository.MedicationRepository

class DeleteScheduleUseCase
    (private val medicationRepository: MedicationRepository) {
    suspend operator fun invoke(schedule: Schedule) {
        medicationRepository.deleteSchedule(
            schedule = schedule
        )
    }
}