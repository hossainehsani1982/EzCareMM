package com.hossain_ehs.medication_domain.use_case

import com.hossain_ehs.medication_domain.model.medication.Medication
import com.hossain_ehs.medication_domain.repository.MedicationRepository

class GetMedicationsOrdByScheduleACSUseCase(
    private val medicationRepository: MedicationRepository
) {
    suspend operator fun invoke() : List<Medication>{
        return medicationRepository.getAllMedicationsOrderByScheduleACS()
    }
}
