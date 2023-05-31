package com.hossain_ehs.medication_domain.model.medicine_data

import com.hossain_ehs.medication_domain.model.medication.MedicineType

data class MedicineData(
    val drugName : String,
    val usageForm : MedicineType,
    val drugStrength : String,
    val medicationDataId : Int = 0
)
