package com.hossain_ehs.medication_data.mapper

import com.hossain_ehs.core.local.entity.MedicationEntity
import com.hossain_ehs.medication_domain.model.medication.Medication
import com.hossain_ehs.medication_domain.model.medication.MedicineType
import com.hossain_ehs.medication_domain.model.medication.MedicationUsageStatus

fun Medication.toMedicationEntity() : MedicationEntity {
   return MedicationEntity(
        userName = userName,
        userId = userId,
        medicationId = medicationId,
        laboratoryId = laboratoryId,
        medicineName = medicineName,
        doctorName = doctorName,
        medicationImageUri = medicationImageUri,
        shortDescription = medicationNotes,
        dosage = medicationDosage,
        startingSchedule = startingSchedule,
        nextSchedule = nextSchedule,
        intervalUnit = intervalUnit,
        intervalValue = intervalValue,
        approximateUsagePeriod = approximateUsagePeriod,
        medicationType = medicationType.type,
        medicationStatus = medicationStatus.status
   )
}

fun MedicationEntity.toMedication() : Medication {
    return Medication(
        userName = userName,
        userId = userId,
        laboratoryId = laboratoryId,
        medicationId = medicationId,
        medicineName = medicineName,
        doctorName = doctorName,
        medicationImageUri = medicationImageUri,
        medicationNotes = shortDescription,
        medicationDosage = dosage,
        startingSchedule = startingSchedule,
        nextSchedule = nextSchedule,
        intervalUnit = intervalUnit,
        intervalValue = intervalValue,
        approximateUsagePeriod = approximateUsagePeriod,
        medicationType = MedicineType.fromString(medicationType),
        medicationStatus = MedicationUsageStatus.fromString(medicationStatus)

    )
}