package com.hossain_ehs.medication_domain.use_case

import com.hossain_ehs.medication_domain.repository.MedicationRepository
import kotlinx.coroutines.flow.Flow

class GetUserNameUseCase (
    private val medicationRepository: MedicationRepository
        ){
    operator fun invoke(userId: Int) : Flow<String> {
       return medicationRepository.getUserName(userId)
    }
}