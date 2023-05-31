package com.hossain_ehs.medication_domain.use_case

import com.example.ezcare.room_tables.relations.MedicationWithSchedules
import com.hossain_ehs.medication_domain.model.schedule.Schedule
import com.hossain_ehs.medication_domain.repository.MedicationRepository

class GetScheduleByMedicationIdUseCase(
    private val medicationRepository: MedicationRepository
) {
    suspend operator fun invoke(medicationId: Int) : List<Schedule>{
      return  medicationRepository.getScheduleByMedicationId(medicationId)
    }
}
