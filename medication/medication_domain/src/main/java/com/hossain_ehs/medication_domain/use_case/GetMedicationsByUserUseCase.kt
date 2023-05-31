package com.hossain_ehs.medication_domain.use_case

import com.hossain_ehs.medication_domain.model.medication.Medication
import com.hossain_ehs.medication_domain.repository.MedicationRepository
import kotlinx.coroutines.flow.Flow

class GetMedicationsByUserUseCase (
    private val repository: MedicationRepository
) {
     operator fun invoke(userId: Int) : Flow<List<Medication>> {
        return repository.getMedicationsByUser(userId)
    }
}
