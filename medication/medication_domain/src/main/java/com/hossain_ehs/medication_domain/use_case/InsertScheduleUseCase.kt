package com.hossain_ehs.medication_domain.use_case

import com.hossain_ehs.medication_domain.model.schedule.Schedule
import com.hossain_ehs.medication_domain.repository.MedicationRepository

class InsertScheduleUseCase(
    private val repository: MedicationRepository,
) {
    suspend operator fun invoke( schedule: Schedule) {
        repository.insertSchedule(schedule)
    }

}
