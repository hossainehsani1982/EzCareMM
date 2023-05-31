package com.hossain_ehs.medication_domain.model.medication

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
sealed class  MedicationUsageStatus(val status: String) : Parcelable {
    object Taken: MedicationUsageStatus("taken")
    object NotTaken: MedicationUsageStatus("not_taken")

    companion object {
        fun fromString(status: String): MedicationUsageStatus {
            return when(status.lowercase()) {
                "taken" -> Taken
                "not_taken" -> NotTaken
                else -> Taken
            }
        }
    }
}
