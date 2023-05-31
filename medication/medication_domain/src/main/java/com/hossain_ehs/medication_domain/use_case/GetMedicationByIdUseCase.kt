package com.hossain_ehs.medication_domain.use_case

import com.hossain_ehs.medication_domain.model.medication.Medication
import com.hossain_ehs.medication_domain.repository.MedicationRepository

class GetMedicationByIdUseCase(
    private val repository: MedicationRepository
) {
    suspend operator fun invoke(id: Int): Medication {
        return repository.getMedicationById(id)
    }
}