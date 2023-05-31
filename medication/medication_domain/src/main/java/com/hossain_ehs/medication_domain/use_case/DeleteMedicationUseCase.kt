package com.hossain_ehs.medication_domain.use_case

import com.hossain_ehs.medication_domain.model.medication.Medication
import com.hossain_ehs.medication_domain.repository.MedicationRepository

class DeleteMedicationUseCase (
    private val repository: MedicationRepository
) {
    suspend operator fun invoke(medication: Medication) {
        repository.deleteMedication(medication)
    }
}