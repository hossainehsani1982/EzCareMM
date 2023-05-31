package com.hossain_ehs.medication_domain.use_case

import com.hossain_ehs.medication_domain.repository.MedicationRepository

class SearchMedicationsByNameUseCase(
    private val medicationRepository: MedicationRepository
) {
     operator fun invoke(searchQuery: String ,userId : Int) =
        medicationRepository.getMedicationsByMedicationName(searchQuery, userId)
}
