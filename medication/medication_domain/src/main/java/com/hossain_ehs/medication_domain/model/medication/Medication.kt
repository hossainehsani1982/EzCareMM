package com.hossain_ehs.medication_domain.model.medication

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Medication(
    val id : Int? = 0,
    val userName: String,
    val userId: Int,
    val laboratoryId: Int,
    val medicationId: Int,
    val medicineName: String,
    val doctorName: String,
    val medicationImageUri: String,
    val medicationNotes: String,
    val medicationDosage: String,
    val startingSchedule: Long,
    val nextSchedule: Long,
    val intervalUnit: Int,
    val intervalValue: Int,
    val approximateUsagePeriod: String,
    val medicationType:  @RawValue MedicineType,
    val medicationStatus:  @RawValue MedicationUsageStatus
    ) : Parcelable

