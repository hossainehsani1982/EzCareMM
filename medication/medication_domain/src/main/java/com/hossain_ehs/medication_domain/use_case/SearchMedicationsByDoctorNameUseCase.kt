package com.hossain_ehs.medication_domain.use_case

import com.hossain_ehs.medication_domain.repository.MedicationRepository

class SearchMedicationsByDoctorNameUseCase(
    private val medicationRepository: MedicationRepository
) {
        operator fun invoke(searchQuery: String ,userId : Int) =
            medicationRepository.getMedicationsByDoctorName(searchQuery, userId)
}