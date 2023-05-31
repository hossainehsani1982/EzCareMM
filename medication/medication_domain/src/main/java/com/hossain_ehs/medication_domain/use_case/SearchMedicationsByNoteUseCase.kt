package com.hossain_ehs.medication_domain.use_case

import com.hossain_ehs.medication_domain.model.medication.Medication
import com.hossain_ehs.medication_domain.repository.MedicationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class SearchMedicationsByNoteUseCase (
    private val medicationRepository: MedicationRepository
        ){
    operator fun invoke(query : String, userId: Int) : Flow<List<Medication>> {
        return medicationRepository.getMedicationsByNoteQuery(query, userId)
    }


    }
