package com.hossain_ehs.medication_domain.use_case

import com.hossain_ehs.medication_domain.repository.MedicationRepository

class GetUserImageUriUseCase (
    private val medicationRepository: MedicationRepository
        ) {
    operator fun invoke(userId : Int): String {
        return medicationRepository.getUserImageUri(userId)
    }
}